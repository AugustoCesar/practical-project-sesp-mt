package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;
import com.sespmt.practicalproject.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepository;

    private long existingId;
    private long nonExistingId;
    private long countTotalPersons;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalPersons = 1;
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

        PersonEntity personEntity = Factory.createPersonEntity();
        personEntity.setId(null);
        AddressEntity addressEntity = Factory.createAddressEntity();
        addressEntity.setId(null);
        personEntity.getAddresses().clear();
        personEntity.getAddresses().add(addressEntity);

        personEntity = personRepository.save(personEntity);

        Assertions.assertNotNull(personEntity.getId());
        Assertions.assertEquals(countTotalPersons + 1, personEntity.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        personRepository.deleteById(existingId);

        Optional<PersonEntity> result = personRepository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalPersonEntityNonEmptyWhenIdExists() {

        Optional<PersonEntity> result = personRepository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalPersonEntityEmptyWhenIdDoesNotExists() {

        Optional<PersonEntity> result = personRepository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());
    }

}
