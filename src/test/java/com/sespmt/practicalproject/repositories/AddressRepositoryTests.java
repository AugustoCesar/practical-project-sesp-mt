package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
    }

    @Test
    public void saveShouldPersistWhenIdIsNull() {

        AddressEntity addressEntity = Factory.createAddressEntity();
        addressEntity.setId(null);

        addressEntity = addressRepository.save(addressEntity);

        Assertions.assertNotNull(addressEntity.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        addressRepository.deleteById(existingId);

        Optional<AddressEntity> result = addressRepository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalAddressEntityNonEmptyWhenIdExists() {

        Optional<AddressEntity> result = addressRepository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnOptionalAddressEntityEmptyWhenIdDoesNotExists() {

        Optional<AddressEntity> result = addressRepository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());
    }

}
