package com.sespmt.practicalproject.services;

import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class PersonServiceTests {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<PersonEntity> page;
    private PersonEntity personEntity;
    private AddressEntity addressEntity;
    private PersonDto personDto;
    private List<AddressEntity> addressEntityList = new ArrayList<>();
    private List<PersonEntity> personEntityList = new ArrayList<>();


    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        personEntity = Factory.createPersonEntity();
        addressEntity = Factory.createAddressEntity();
        personDto = Factory.createPersonDto();
        page = new PageImpl<>(List.of(personEntity));
        addressEntityList.add(addressEntity);
        personEntityList.add(personEntity);

        Mockito.when(personRepository.findAll((Pageable) any())).thenReturn(page);

        Mockito.when(personRepository.save(any())).thenReturn(personEntity);

        Mockito.when(personRepository.findById(existingId)).thenReturn(Optional.of(personEntity));
        Mockito.when(personRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(personRepository.searchFiltered(any(), any(), any(), any())).thenReturn(page);

        Mockito.when(personRepository.findByNameIgnoreCaseContaining(any(), any())).thenReturn(page);

        Mockito.when(personRepository.findByCpf(any(), any())).thenReturn(page);

        Mockito.doNothing().when(personRepository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(personRepository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(personRepository).deleteById(dependentId);

        Mockito.when(personRepository.findById(existingId)).thenReturn(Optional.ofNullable(personEntity));
        Mockito.when(personRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(personRepository.findByIdIn(List.of(existingId))).thenReturn(personEntityList);
        Mockito.when(personRepository.findByIdIn(List.of(nonExistingId))).thenThrow(EntityNotFoundException.class);

        Mockito.when(addressRepository.findById(existingId)).thenReturn(Optional.ofNullable(addressEntity));
        Mockito.when(addressRepository.findById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        Mockito.when(addressRepository.searchByCity(any(), any())).thenReturn(addressEntityList);
    }

    @Test
    public void findByIdShouldReturnPersonDtoPageWhenIdExists() {

        Page<PersonDto> result = personService.findById(existingId);

        Assertions.assertNotNull(result);
    }

    @Test
    public void findByIdShouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            personService.findById(nonExistingId);
        });
    }

    @Test
    public void updateShouldReturnPersonDtoPageWhenIdExists() {

        Page<PersonDto> result = personService.update(existingId, personDto);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personService.update(nonExistingId, personDto);
        });
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            personService.delete(existingId);
        });

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            personService.delete(nonExistingId);
        });

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            personService.delete(dependentId);
        });

        Mockito.verify(personRepository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<PersonDto> result = personService.findAllPaged(pageable);

        Assertions.assertNotNull(result);

    }

    @Test
    public void searchFilteredShouldReturnPageFiltered() {

        Pageable pageable = PageRequest.of(0, 10);
        String name = "Ale";
        LocalDate birthDate = LocalDate.of(1990, 3, 18);
        String motherName = "Bro";

        Page<PersonDto> result = personService.searchFiltered(name, birthDate, motherName, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(page.getContent().size(), result.getContent().size());
    }

    @Test
    public void searchPersonByCityShouldReturnPage() {

        String state = "MT";
        String city = "Cuiabá";

        Page<PersonDto> result = personService.searchPersonByCity(state, city);

        Mockito.verify(addressRepository, Mockito.times(1)).searchByCity(eq(state), eq(city));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(personEntityList.size(), result.getContent().size());
    }

    @Test
    public void findByNameShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);
        String name = "alex";

        Page<PersonDto> result = personService.findByName(name, pageable);

        Mockito.verify(personRepository, Mockito.times(1))
                .findByNameIgnoreCaseContaining(eq(name), eq(pageable));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(page.getContent().size(), result.getContent().size());
    }

    @Test
    public void findByCpfShouldReturnPage() {

        String cpf = "50800184025";

        Page<PersonDto> result = personService.findByCpf(cpf);

        Mockito.verify(personRepository, Mockito.times(1))
                .findByCpf(eq(cpf), eq(PageRequest.of(0, 1)));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(page.getContent().size(), result.getContent().size());
    }
}
