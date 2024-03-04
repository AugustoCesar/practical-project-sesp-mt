package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.PersonEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class PersonService {

    @Autowired
    private PersonRepository repository;

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);


    @Transactional(readOnly = true)
    public Page<PersonDto> findAllPaged(Pageable pageable) {
        Page<PersonEntity> page = repository.findAll(pageable);
        return page.map(personMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        Optional<PersonEntity> obj = repository.findById(id);
        PersonEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return personMapper.toDto(entity);
    }

    @Transactional
    public PersonDto insert(PersonDto dto) {
        PersonEntity entity = new PersonEntity();
        personMapper.toEntity(dto);
        entity = repository.save(entity);
        return personMapper.toDto(entity);
    }

    @Transactional
    public PersonDto update(Long id, PersonDto dto) {
        try {
            PersonEntity entity = personMapper.toEntity(findById(id));
            buildPersonUpdated(dto, entity);
            entity = repository.save(entity);
            return personMapper.toDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integry violation");
        }
    }

    private void buildPersonUpdated(PersonDto dto, PersonEntity entity) {

        entity.setName(dto.getName());
        entity.setRg(dto.getRg());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setMotherName(dto.getMotherName());
        entity.setFatherName(dto.getFatherName());
        entity.setUpdatedAt(LocalDateTime.now());

        entity.getAddress().setPublicPlace(dto.getAddress().getPublicPlace());
        entity.getAddress().setNeighborhood(dto.getAddress().getNeighborhood());
        entity.getAddress().setNumber(dto.getAddress().getNumber());
        entity.getAddress().setCity(dto.getAddress().getCity());
        entity.getAddress().setState(dto.getAddress().getState());
        entity.getAddress().setPostalCode(dto.getAddress().getPostalCode());

    }
}
