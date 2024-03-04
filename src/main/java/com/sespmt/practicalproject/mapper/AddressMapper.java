package com.sespmt.practicalproject.mapper;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.entities.AddressEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    AddressEntity toEntity(AddressDto personDto);

    AddressDto toDto(AddressEntity personEntity);
}
