package com.sespmt.practicalproject.controllers;

import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.dto.RequestCpfDto;
import com.sespmt.practicalproject.services.PersonService;
import com.sespmt.practicalproject.util.Util;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<Page<PersonDto>> findAllPaged(Pageable pageable) {

        Page<PersonDto> page = personService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/search-by-filter")
    public ResponseEntity<Page<PersonDto>> searchFiltered(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "birthDate", defaultValue = "") String birthDate,
            @RequestParam(value = "motherName", defaultValue = "") String motherName,
            Pageable pageable) {
        birthDate = Util.decodeParam(birthDate);
        LocalDate newDate = birthDate.isEmpty() ? null : Util.convertDate(birthDate);

        Page<PersonDto> page = personService.searchFiltered(name, newDate, motherName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/search-by-name")
    public ResponseEntity<Page<PersonDto>> findByName(
            @RequestParam(value = "name", defaultValue = "") String name,
            Pageable pageable) {

        Page<PersonDto> page = personService.findByName(name, pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/search-by-cpf")
    public ResponseEntity<Page<PersonDto>> findByCpf(
            @Valid @RequestBody RequestCpfDto dto,
            Pageable pageable) {

        Page<PersonDto> page = personService.findByCpf(dto.getCpf(), pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<PersonDto>> findById(@PathVariable Long id) {
        Page<PersonDto> page = personService.findById(id);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    public ResponseEntity<PersonDto> insert(@Valid @RequestBody PersonDto dto) {
        PersonDto newDto = personService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDto> update(@Valid @PathVariable Long id, @Valid @RequestBody PersonDto dto) {
        PersonDto newDto = personService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
