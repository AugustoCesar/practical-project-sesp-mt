package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
import com.sespmt.practicalproject.mapper.AddressMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);


    @Transactional(readOnly = true)
    public List<AddressDto> searchByCity(String state, String city) {

        List<AddressEntity> searchResult = addressRepository.searchByCity(state, city);

        return searchResult.stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AddressDto> findAllPaged(Pageable pageable) {
        Page<AddressEntity> page = addressRepository.findAll(pageable);

        return buildAddressDtoPageReturn(page);
    }

    @Transactional(readOnly = true)
    public Page<AddressDto> findById(Long id) {
        Optional<AddressEntity> obj = addressRepository.findById(id);
        AddressEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        List<AddressEntity> entityList = Collections.singletonList(entity);
        Page<AddressEntity> page = new PageImpl<>(entityList, PageRequest.of(0, 10), entityList.size());
        return buildAddressDtoPageReturn(page);
    }

    @Transactional
    public Page<AddressDto> insert(AddressDto dto) {
        if (dto.getPersonId() == null) {
            throw new DatabaseException("personId deve ser diferente de null");
        }
        try {
            Optional<PersonEntity> personEntityOpt = personRepository.findById(dto.getPersonId());
            PersonEntity personEntity = personEntityOpt.orElseThrow(() ->
                    new ResourceNotFoundException("Person entity not found by id: " + dto.getPersonId()));

            AddressEntity entity = addressMapper.toEntity(dto);

            personEntity.getAddresses().add(entity);
            personRepository.save(personEntity);

            List<AddressEntity> entityList = Collections.singletonList(entity);
            Page<AddressEntity> page = new PageImpl<>(
                    entityList,
                    PageRequest.of(0, 10),
                    entityList.size()
            );

            return buildAddressDtoPageReturn(page);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public Page<AddressDto> update(Long id, AddressDto dto) {
        if (dto.getPersonId() == null) {
            throw new DatabaseException("personId deve ser diferente de null");
        }
        try {
            Optional<AddressEntity> obj = addressRepository.findById(id);
            AddressEntity entity = obj.orElseThrow(() ->
                    new ResourceNotFoundException("Address entity not found by id: " + id));

            if (!dto.getPersonId().equals(entity.getPerson().getId())) {
                throw new DatabaseException("personId não pertencente a este address");
            }

            Optional<PersonEntity> personEntityOpt = personRepository.findById(entity.getPerson().getId());
            PersonEntity personEntity = personEntityOpt.orElseThrow(() ->
                    new ResourceNotFoundException("Person entity not found by id: " + dto.getPersonId()));

            personEntity.getAddresses()
                    .stream()
                    .map(addressEntity -> {
                        if (addressEntity.getId().equals(id)) {
                            buildAddressToUpdate(dto, entity);
                        }
                        return addressEntity;
                    });

            personEntity = personRepository.save(personEntity);

            AddressEntity address = personEntity.getAddresses().stream()
                    .filter(addressEntity -> addressEntity.getId().equals(id)).findFirst().orElse(null);

//            buildAddressToUpdate(dto, entity);

//            entity = addressRepository.save(entity);


            List<AddressEntity> entityList = Collections.singletonList(address);
            Page<AddressEntity> page = new PageImpl<>(
                    entityList,
                    PageRequest.of(0, 10),
                    entityList.size()
            );

            return buildAddressDtoPageReturn(page);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            addressRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void buildAddressToUpdate(AddressDto dto, AddressEntity entity) {

        entity.setPublicPlace(dto.getPublicPlace());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setNumber(dto.getNumber());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPostalCode(dto.getPostalCode());

    }

    private Page<AddressDto> buildAddressDtoPageReturn(Page<AddressEntity> addressEntityPage) {

        return addressEntityPage.map(addressEntity -> {
            if (addressEntity.getPerson() != null) {
                Long personId = addressEntity.getPerson().getId();
                addressEntity.setPerson(null);
                AddressDto addressDto = addressMapper.toDto(addressEntity);
                PersonDto personDto = new PersonDto();
                personDto.setId(personId);
                addressDto.setPersonId(personId);
                return addressDto;
            } else {
                return addressMapper.toDto(addressEntity);
            }
        });
    }
}
