package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.mapper.AddressMapper;
import com.sespmt.practicalproject.repositories.AddressRepository;
import com.sespmt.practicalproject.services.exceptions.DatabaseException;
import com.sespmt.practicalproject.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.factory.Mappers;
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

    @Autowired
    private AddressRepository addressRepository;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);


    @Transactional(readOnly = true)
    public List<AddressDto> searchByCity(String state, String city) {

        List<AddressEntity> searchResult = addressRepository.searchByCity(state, city);

        return searchResult.stream().map(addressMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AddressDto> findAllPaged(Pageable pageable) {
        Page<AddressEntity> page = addressRepository.findAll(pageable);
        return page.map(addressMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AddressDto> findById(Long id) {
        Optional<AddressEntity> obj = addressRepository.findById(id);
        AddressEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        List<AddressEntity> entityList = Collections.singletonList(entity);
        Page<AddressEntity> page = new PageImpl<>(entityList, PageRequest.of(0, 10), entityList.size());
        return page.map(addressMapper::toDto);
    }

    @Transactional
    public AddressDto insert(AddressDto dto) {
        AddressEntity entity = addressMapper.toEntity(dto);
        entity = addressRepository.save(entity);
        return addressMapper.toDto(entity);
    }

    @Transactional
    public AddressDto update(Long id, AddressDto dto) {
        try {
            Optional<AddressEntity> obj = addressRepository.findById(id);
            AddressEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
            buildAddressToUpdate(dto, entity);
            entity = addressRepository.save(entity);
            return addressMapper.toDto(entity);
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
}
