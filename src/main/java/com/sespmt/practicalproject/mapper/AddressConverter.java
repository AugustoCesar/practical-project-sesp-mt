package com.sespmt.practicalproject.mapper;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import com.sespmt.practicalproject.entities.PersonEntity;

import java.util.HashSet;
import java.util.Set;

public class AddressConverter {

    public AddressEntity toEntity(AddressDto addressDto) {
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
        addressEntity.setPerson(personDtoToPersonEntity(addressDto.getPerson()));

        return addressEntity;
    }

    public AddressDto toDto(AddressEntity addressEntity) {
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

        return addressDto;
    }

    private Set<AddressEntity> addressDtoSetToAddressEntitySet(Set<AddressDto> addressDtoSet) {
        if (addressDtoSet == null) {
            return null;
        }

        Set<AddressEntity> addressEntitySet = new HashSet<>();
        for (AddressDto addressDto : addressDtoSet) {
            addressEntitySet.add(toEntity(addressDto));
        }

        return addressEntitySet;
    }

    private PersonEntity personDtoToPersonEntity(PersonDto personDto) {
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
}
