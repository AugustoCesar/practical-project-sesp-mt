package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
import com.sespmt.practicalproject.mapper.AddressMapper;
import com.sespmt.practicalproject.repositories.AddressRepository;
import com.sespmt.practicalproject.repositories.PersonRepository;
import com.sespmt.practicalproject.services.exceptions.DatabaseException;
import com.sespmt.practicalproject.services.exceptions.ResourceNotFoundException;
import com.sespmt.practicalproject.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class AddressServiceTests {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressMapper addressMapper;

    private Long existingId;
    private Long nonExistingId;
    private PageImpl<AddressEntity> page;
    private AddressEntity addressEntity;
    private AddressDto addressDto;
    private List<AddressEntity> addressEntityList = new ArrayList<>();
    private PersonEntity personEntity;
    private Long existingPersonId;
    private Long nonExistingPersonId;


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        addressEntity = Factory.createAddressEntity();
        addressDto = Factory.createAddressDto();
        page = new PageImpl<>(List.of(addressEntity));
        addressEntityList.add(addressEntity);
        personEntity = Factory.createPersonEntity();
        existingPersonId = 1L;
        nonExistingPersonId = 1000L;

        Mockito.when(addressRepository.findAll((Pageable) any())).thenReturn(page);

        Mockito.when(addressRepository.save(any())).thenReturn(addressEntity);

        Mockito.when(addressRepository.findById(existingId)).thenReturn(Optional.of(addressEntity));
        Mockito.when(addressRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(addressRepository.searchByCity(any(), any())).thenReturn(addressEntityList);

        Mockito.doNothing().when(addressRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(addressRepository).deleteById(nonExistingId);

        Mockito.when(addressRepository.findById(existingId)).thenReturn(Optional.ofNullable(addressEntity));
        Mockito.when(addressRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(addressMapper.toEntity(addressDto)).thenReturn(addressEntity);
        Mockito.when(addressMapper.toDto(addressEntity)).thenReturn(addressDto);

        Mockito.when(personRepository.findById(existingPersonId)).thenReturn(Optional.ofNullable(personEntity));
        Mockito.when(personRepository.findById(nonExistingPersonId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(personRepository.save(any())).thenReturn(personEntity);


    }

    @Test
    public void findByIdShouldReturnAddressDtoPageWhenIdExists() {

        Page<AddressDto> result = addressService.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            addressService.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnPageWithContentWhenFieldsAreCorrect() {

        addressDto.setPersonId(existingPersonId);

        Page<AddressDto> result = addressService.insert(addressDto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(addressEntityList.size(), result.getContent().size());
    }

    @Test
    public void insertShouldThrowDatabaseExceptionWhenPersonIdIsNull() {

        addressDto.setPersonId(null);

        Assertions.assertThrows(DatabaseException.class, () -> {
            addressService.insert(addressDto);
        });
    }

    @Test
    public void insertShouldThrowResourceNotFoundExceptionWhenPersonIdDoesNotExists() {

        addressDto.setPersonId(nonExistingPersonId);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            addressService.insert(addressDto);
        });
    }

    @Test
    public void updateShouldReturnAddressDtoPageWhenIdExists() {

        Page<AddressDto> result = addressService.update(existingId, addressDto);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            addressService.update(nonExistingId, addressDto);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            addressService.delete(existingId);
        });

        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            addressService.delete(nonExistingId);
        });

        Mockito.verify(addressRepository, Mockito.times(1)).deleteById(nonExistingId);
    }


    @Test
    public void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<AddressDto> result = addressService.findAllPaged(pageable);

        Assertions.assertNotNull(result);

    }

    @Test
    public void searchByCityShouldReturnList() {

        String state = "MT";
        String city = "Cuiabá";

        List<AddressDto> result = addressService.searchByCity(state, city);

        Mockito.verify(addressRepository, Mockito.times(1)).searchByCity(eq(state), eq(city));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(addressEntityList.size(), result.size());
    }
}
