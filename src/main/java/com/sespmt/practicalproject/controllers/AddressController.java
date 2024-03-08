package com.sespmt.practicalproject.controllers;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.services.AddressService;
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

@RestController
@RequestMapping(value = "/addresses")
@Tag(name = "Endereços", description = "Endpoints para operações relacionadas a endereços (criação, leitura, atualização e remoção)")
public class AddressController {

    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressService addressService;

    @Operation(description = "Consulta paginada de endereços cadastrados")
    @GetMapping
    public ResponseEntity<Page<AddressDto>> findAllPaged(Pageable pageable) {

        Page<AddressDto> page = addressService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }


    @Operation(description = "Consulta paginada de endereço por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<AddressDto>> findById(@PathVariable Long id) {
        Page<AddressDto> page = addressService.findById(id);
        return ResponseEntity.ok().body(page);
    }

    @Operation(description = "Cadastro de endereço")
    @PostMapping
    public ResponseEntity<Page<AddressDto>> insert(
            @Schema(description = "Corpo da requisição - dados do endereço a ser cadastrado",
                    implementation = AddressDto.class)
            @Valid @RequestBody AddressDto dto) {
        Page<AddressDto> newPageDto = addressService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("{/id}")
                .buildAndExpand(newPageDto.getContent().get(0).getId())
                .toUri();

        return ResponseEntity.created(uri).body(newPageDto);
    }

    @Operation(description = "Atualização de dados de um endereço pelo ID")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Page<AddressDto>> update(
            @Schema(description = "ID do endereço a ser atualizado os dados")
            @Valid @PathVariable Long id,
            @Schema(description = "Corpo da requisição - dados do endereço a ser atualizado",
                    implementation = AddressDto.class)
            @Valid @RequestBody AddressDto dto) {
        Page<AddressDto> newPageDto = addressService.update(id, dto);
        return ResponseEntity.ok().body(newPageDto);
    }

    @Operation(description = "Remoção de cadastro de endereço pelo ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @Schema(description = "ID do endereço a ser removido")
            @PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
