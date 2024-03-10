package com.sespmt.practicalproject.tests;


import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Factory {
    public static PersonEntity createPersonEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setId(1L);
        personEntity.setName("Alex Brown");
        personEntity.setRg("111222333");
        personEntity.setCpf("50800184025");
        personEntity.setBirthDate(LocalDate.of(1990, 3, 18));
        personEntity.setPhoneNumber("6598887755");
        personEntity.setMotherName("Maria Brown");
        personEntity.setFatherName("José Brown");
        personEntity.setCreatedAt(LocalDateTime.now());
        personEntity.getAddresses().add(createAddressEntity());
        return personEntity;
    }

    public static PersonDto createPersonDto() {
        PersonDto personDto = new PersonDto();
        personDto.setId(1L);
        personDto.setName("Alex Brown");
        personDto.setRg("111222333");
        personDto.setCpf("50800184025");
        personDto.setBirthDate(LocalDate.of(1990, 3, 18));
        personDto.setPhoneNumber("6598887755");
        personDto.setMotherName("Maria Brown");
        personDto.setFatherName("José Brown");
        personDto.setCreatedAt(LocalDateTime.now());
        personDto.getAddresses().add(createAddressDto());

        return personDto;
    }

    public static AddressEntity createAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setPublicPlace("Rua da Saudade");
        addressEntity.setNeighborhood("Campo Verde");
        addressEntity.setNumber(999);
        addressEntity.setCity("Cuiabá");
        addressEntity.setState("MT");
        addressEntity.setPostalCode("78050-350");
        addressEntity.setPerson(new PersonEntity(1L, null, null, null, null, null, null, null, null, null));
        return addressEntity;
    }

    public static AddressDto createAddressDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1L);
        addressDto.setPublicPlace("Rua da Saudade");
        addressDto.setNeighborhood("Campo Verde");
        addressDto.setNumber(999);
        addressDto.setCity("Cuiabá");
        addressDto.setState("MT");
        addressDto.setPostalCode("78050-350");
        addressDto.setPersonId(1L);
        return addressDto;
    }
}
