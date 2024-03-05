package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
import com.sespmt.practicalproject.mapper.AddressMapper;
import com.sespmt.practicalproject.mapper.PersonMapper;
import com.sespmt.practicalproject.repositories.PersonRepository;
import com.sespmt.practicalproject.services.exceptions.DatabaseException;
import com.sespmt.practicalproject.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);


    @Transactional(readOnly = true)
    public Page<PersonDto> findAllPaged(Pageable pageable) {
        Page<PersonEntity> page = personRepository.findAll(pageable);
        return page.map(personMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        Optional<PersonEntity> obj = personRepository.findById(id);
        PersonEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return personMapper.toDto(entity);
    }

    @Transactional
    public PersonDto insert(PersonDto dto) {
        PersonEntity entity = personMapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity = personRepository.save(entity);
        return personMapper.toDto(entity);
    }

    @Transactional
    public PersonDto update(Long id, PersonDto dto) {
        try {
            PersonEntity entity = personMapper.toEntity(findById(id));
            buildPersonToUpdate(dto, entity);
            entity = personRepository.save(entity);
            return personMapper.toDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integry violation");
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
            AddressEntity addressEntity = addressMapper.toEntity(addressDto);
            entity.getAddresses().add(addressEntity);
        }
    }
}
