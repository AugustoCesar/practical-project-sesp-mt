package com.sespmt.practicalproject.mapper;

import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.entities.PersonEntity;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    PersonEntity toEntity(PersonDto personDto);

    PersonDto toDto(PersonEntity personEntity);
}
