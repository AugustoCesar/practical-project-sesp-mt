package com.sespmt.practicalproject.mapper;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;

import java.util.HashSet;
import java.util.Set;

public class PersonConverter {

    public PersonEntity toEntity(PersonDto personDto) {
        if (personDto == null) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setId(personDto.getId());
        personEntity.setName(personDto.getName());
        personEntity.setRg(personDto.getRg());
        personEntity.setCpf(personDto.getCpf());
        personEntity.setBirthDate(personDto.getBirthDate());
        personEntity.setPhoneNumber(personDto.getPhoneNumber());
        personEntity.setMotherName(personDto.getMotherName());
        personEntity.setFatherName(personDto.getFatherName());
        personEntity.setCreatedAt(personDto.getCreatedAt());
        personEntity.setUpdatedAt(personDto.getUpdatedAt());
        if (personEntity.getAddress() != null) {
            Set<AddressEntity> set = addressDtoSetToAddressEntitySet(personDto.getAddress());
            if (set != null) {
                personEntity.getAddress().addAll(set);
            }
        }

        return personEntity;
    }

    public PersonDto toDto(PersonEntity personEntity) {
        if (personEntity == null) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        personDto.setId(personEntity.getId());
        personDto.setName(personEntity.getName());
        personDto.setRg(personEntity.getRg());
        personDto.setCpf(personEntity.getCpf());
        personDto.setBirthDate(personEntity.getBirthDate());
        personDto.setPhoneNumber(personEntity.getPhoneNumber());
        personDto.setMotherName(personEntity.getMotherName());
        personDto.setFatherName(personEntity.getFatherName());
        personDto.setCreatedAt(personEntity.getCreatedAt());
        personDto.setUpdatedAt(personEntity.getUpdatedAt());
        personDto.setAddress(addressEntitySetToAddressDtoSet(personEntity.getAddress()));

        return personDto;
    }

    private AddressEntity addressDtoToAddressEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }

        AddressEntity addressEntity = new AddressEntity();

        addressEntity.setId(addressDto.getId());
        addressEntity.setPublicPlace(addressDto.getPublicPlace());
        addressEntity.setNeighborhood(addressDto.getNeighborhood());
        addressEntity.setNumber(addressDto.getNumber());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setState(addressDto.getState());
        addressEntity.setPostalCode(addressDto.getPostalCode());
        addressEntity.setPerson(toEntity(addressDto.getPerson()));

        return addressEntity;
    }

    private Set<AddressEntity> addressDtoSetToAddressEntitySet(Set<AddressDto> set) {
        if (set == null) {
            return null;
        }

        Set<AddressEntity> addressEntitySet = new HashSet<>();
        for (AddressDto addressDto : set) {
            addressEntitySet.add(addressDtoToAddressEntity(addressDto));
        }

        return addressEntitySet;
    }

    private AddressDto addressEntityToAddressDto(AddressEntity addressEntity) {
        if (addressEntity == null) {
            return null;
        }

        AddressDto addressDto = new AddressDto();

        addressDto.setId(addressEntity.getId());
        addressDto.setPublicPlace(addressEntity.getPublicPlace());
        addressDto.setNeighborhood(addressEntity.getNeighborhood());
        addressDto.setNumber(addressEntity.getNumber());
        addressDto.setCity(addressEntity.getCity());
        addressDto.setState(addressEntity.getState());
        addressDto.setPostalCode(addressEntity.getPostalCode());
        addressDto.setPerson(null);

        return addressDto;
    }

    private Set<AddressDto> addressEntitySetToAddressDtoSet(Set<AddressEntity> addressEntitySet) {
        if (addressEntitySet == null) {
            return null;
        }

        Set<AddressDto> addressDtoSet = new HashSet<>();
        for (AddressEntity addressEntity : addressEntitySet) {
            addressDtoSet.add(addressEntityToAddressDto(addressEntity));
        }

        return addressDtoSet;
    }
}
