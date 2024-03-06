package com.sespmt.practicalproject.controllers;

import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.services.AddressService;
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
public class AddressController {

    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressDto>> findAllPaged(Pageable pageable) {

        Page<AddressDto> page = addressService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Page<AddressDto>> findById(@PathVariable Long id) {
        Page<AddressDto> page = addressService.findById(id);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    public ResponseEntity<AddressDto> insert(@Valid @RequestBody AddressDto dto) {
        AddressDto newDto = addressService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDto> update(@Valid @PathVariable Long id, @Valid @RequestBody AddressDto dto) {
        AddressDto newDto = addressService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
