package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
import com.sespmt.practicalproject.mapper.AddressMapper;
import com.sespmt.practicalproject.mapper.PersonMapper;
import com.sespmt.practicalproject.repositories.AddressRepository;
import com.sespmt.practicalproject.repositories.PersonRepository;
import com.sespmt.practicalproject.services.exceptions.DatabaseException;
import com.sespmt.practicalproject.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);


    @Transactional(readOnly = true)
    public Page<PersonDto> findAllPaged(Pageable pageable) {
        Page<PersonEntity> page = personRepository.findAll(pageable);

        return buildPersonDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> searchFiltered(String name,
                                          LocalDate birthDate,
                                          String motherName,
                                          Pageable pageable) {
        Page<PersonEntity> page = personRepository.searchFiltered(name, birthDate, motherName, pageable);

        return buildPersonDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> searchPersonByCity(String state,
                                              String city) {
        List<AddressEntity> searchResult = addressRepository.searchByCity(state, city);

        List<Long> idList = searchResult.stream()
                .map(AddressEntity::getPerson)
                .filter(Objects::nonNull)
                .map(PersonEntity::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<PersonEntity> personEntityList = personRepository.findByIdIn(idList);

        Page<PersonEntity> page = new PageImpl<>(
                personEntityList,
                PageRequest.of(0, 10),
                personEntityList.size()
        );
        return buildPersonDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> findById(Long id) {
        Optional<PersonEntity> obj = personRepository.findById(id);
        PersonEntity entity = obj.orElseThrow(() ->
                new ResourceNotFoundException("Person entity not found by id: " + id));

        List<PersonEntity> entityList = Collections.singletonList(entity);
        Page<PersonEntity> page = new PageImpl<>(
                entityList,
                PageRequest.of(0, 10),
                entityList.size()
        );
        return buildPersonDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> findByName(String name, Pageable pageable) {
        Page<PersonEntity> page = personRepository.findByNameIgnoreCaseContaining(name, pageable);
        return buildPersonDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> findByCpf(String cpf) {
        Page<PersonEntity> page = personRepository.findByCpf(cpf, PageRequest.ofSize(1));

        return buildPersonDtoPageReturn(page);
    }

    @Transactional
    public Page<PersonDto> insert(PersonDto dto) {
        verifyExistingFields(dto);

        PersonEntity entity = personMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity = personRepository.save(entity);

        List<PersonEntity> entityList = Collections.singletonList(entity);
        Page<PersonEntity> page = new PageImpl<>(
                entityList,
                PageRequest.of(0, 10),
                entityList.size()
        );

        return buildPersonDtoPageReturn(page);
    }

    @Transactional
    public Page<PersonDto> update(Long id, PersonDto dto) {
        try {
            Optional<PersonEntity> obj = personRepository.findById(id);
            PersonEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
            buildPersonToUpdate(dto, entity);
            entity = personRepository.save(entity);

            List<PersonEntity> entityList = Collections.singletonList(entity);
            Page<PersonEntity> page = new PageImpl<>(
                    entityList,
                    PageRequest.of(0, 10),
                    entityList.size()
            );

            return buildPersonDtoPageReturn(page);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DatabaseException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    public void delete(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void buildPersonToUpdate(PersonDto dto, PersonEntity entity) {
        entity.setName(dto.getName());
        entity.setRg(dto.getRg());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setMotherName(dto.getMotherName());
        entity.setFatherName(dto.getFatherName());
        entity.setUpdatedAt(LocalDateTime.now());

        entity.getAddresses().clear();
        for (AddressDto addressDto : dto.getAddresses()) {
            entity.getAddresses().add(addressMapper.toEntity(addressDto));
        }
    }

    private void verifyExistingFields(PersonDto personDto) {
        String cpf = personDto.getCpf();
        String motherName = personDto.getMotherName();

        Page<PersonEntity> pageCpf = personRepository.findByCpf(cpf, PageRequest.ofSize(1));

        Optional<PersonEntity> opt = personRepository.findByMotherNameIgnoreCase(motherName);

        List<String> errorList = new ArrayList<>();

        if (pageCpf.getTotalElements() > 0) {
            LOG.info("CPF já cadastrado");
            errorList.add("CPF já cadastrado");
        }
        if (opt.isPresent()) {
            LOG.info("Nome de Mãe já cadastrado");
            errorList.add("Nome de mãe já cadastrado");
        }
        if (!errorList.isEmpty()) {
            throw new DatabaseException(errorList.toString());
        }
    }

    private Page<PersonDto> buildPersonDtoPageReturn(Page<PersonEntity> personEntityPage) {
        Page<PersonEntity> page = personEntityPage
                .map(personEntity -> {
                    personEntity.getAddresses().forEach(addressEntity -> {
                        if (addressEntity.getPerson() != null) {
                            addressEntity.setPerson(null);
                        }
                    });
                    return personEntity;
                });
        return page.map(personMapper::toDto);
    }
}
