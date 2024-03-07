package com.sespmt.practicalproject.controllers;

import com.sespmt.practicalproject.dto.CpfDto;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.services.PersonService;
import com.sespmt.practicalproject.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pessoas", description = "Endpoints para operações relacionadas a pessoas (criação, leitura, atualização e remoção)")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonService personService;

    @Operation(description = "Consulta paginada de pessoas cadastrados")
    @GetMapping
    public ResponseEntity<Page<PersonDto>> findAllPaged(Pageable pageable) {
        Page<PersonDto> page = personService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Consulta paginada de pessoas por cidade/estado")
    @GetMapping(value = "/search-by-city")
    public ResponseEntity<Page<PersonDto>> searchByCity(
            @Schema(description = "Unidade Federativa - UF")
            @RequestParam(value = "state", required = false) String state,
            @Schema(description = "Cidade")
            @RequestParam(value = "city", defaultValue = "") String city) {
        Page<PersonDto> page = personService.searchPersonByCity(state, city);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Consulta paginada de pessoas por nome, data de nascimento, nome da mãe")
    @GetMapping(value = "/search-by-filter")
    public ResponseEntity<Page<PersonDto>> searchFiltered(
            @Schema(description = "Nome da pessoa")
            @RequestParam(value = "name", required = false) String name,
            @Schema(description = "Data de nascimento da pessoa")
            @RequestParam(value = "birthDate", required = false) String birthDate,
            @Schema(description = "Nome da mãe da pessoa")
            @RequestParam(value = "motherName", required = false) String motherName,
            Pageable pageable) {
        birthDate = Util.decodeParam(birthDate);
        LocalDate newDate = birthDate.isEmpty() ? null : Util.convertDate(birthDate);

        Page<PersonDto> page = personService.searchFiltered(name, newDate, motherName, pageable);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Consulta paginada de pessoas por nome")
    @GetMapping(value = "/search-by-name")
    public ResponseEntity<Page<PersonDto>> findByName(
            @Schema(description = "Nome da pessoa")
            @RequestParam(value = "name") String name,
            Pageable pageable) {
        Page<PersonDto> page = personService.findByName(name, pageable);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Consulta paginada de pessoa por CPF")
    @PostMapping(value = "/search-by-cpf")
    public ResponseEntity<Page<PersonDto>> findByCpf(
            @Schema(description = "Número de CPF a ser consultado",
                    name = "cpf",
                    implementation = CpfDto.class)
            @Valid @RequestBody CpfDto dto) {
        Page<PersonDto> page = personService.findByCpf(dto.getCpf());
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Consulta paginada de pessoa por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<PersonDto>> findById(
            @Schema(description = "ID a ser consultado")
            @PathVariable Long id) {
        Page<PersonDto> page = personService.findById(id);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Cadastro de pessoa")
    @PostMapping
    public ResponseEntity<PersonDto> insert(
            @Schema(description = "Corpo da requisição - dados da pessoa a ser cadastrada",
                    implementation = PersonDto.class)
            @Valid @RequestBody PersonDto dto) {
        PersonDto newDto = personService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{/id}")
                .buildAndExpand(newDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @Operation(description = "Atualização de dados de uma pessoa pelo ID")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PersonDto> update(
            @Schema(description = "ID da pessoa a ser atualizada os dados")
            @Valid @PathVariable Long id,
            @Schema(description = "Corpo da requisição - dados da pessoa a ser atualizada",
                    implementation = PersonDto.class)
            @Valid @RequestBody PersonDto dto) {
        PersonDto newDto = personService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @Operation(description = "Remoção de cadastro de pessoa pelo ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Schema(description = "ID da pessoa a ser removido os dados")
            @PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
