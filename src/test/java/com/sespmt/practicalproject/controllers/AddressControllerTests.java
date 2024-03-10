package com.sespmt.practicalproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sespmt.practicalproject.dto.AddressDto;
import com.sespmt.practicalproject.services.AddressService;
import com.sespmt.practicalproject.services.exceptions.DatabaseException;
import com.sespmt.practicalproject.services.exceptions.ResourceNotFoundException;
import com.sespmt.practicalproject.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private AddressDto addressDto;
    private PageImpl<AddressDto> page;


    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        addressDto = Factory.createAddressDto();
        page = new PageImpl<>(List.of(addressDto));

        when(addressService.findAllPaged(any())).thenReturn(page);

        when(addressService.findById(existingId)).thenReturn(page);
        when(addressService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(addressService.update(eq(existingId), any())).thenReturn(page);
        when(addressService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(addressService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(addressService).delete(nonExistingId);
        doThrow(DatabaseException.class).when(addressService).delete(dependentId);

        when(addressService.insert(any())).thenReturn(page);
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/addresses/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/addresses/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnAddressDtoCreated() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(addressDto);

        ResultActions result =
                mockMvc.perform(post("/addresses")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].publicPlace").exists());
        result.andExpect(jsonPath("$.content[0].neighborhood").exists());
        result.andExpect(jsonPath("$.content[0].number").exists());
        result.andExpect(jsonPath("$.content[0].city").exists());
        result.andExpect(jsonPath("$.content[0].state").exists());
        result.andExpect(jsonPath("$.content[0].postalCode").exists());
    }

    @Test
    public void updateShouldReturnAddressDtoWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(addressDto);

        ResultActions result =
                mockMvc.perform(put("/addresses/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].publicPlace").exists());
        result.andExpect(jsonPath("$.content[0].neighborhood").exists());
        result.andExpect(jsonPath("$.content[0].number").exists());
        result.andExpect(jsonPath("$.content[0].city").exists());
        result.andExpect(jsonPath("$.content[0].state").exists());
        result.andExpect(jsonPath("$.content[0].postalCode").exists());
    }

    @Test
    public void updateShouldReturnResourceNotFoundWhenIdDoesNotExist() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(addressDto);

        ResultActions result =
                mockMvc.perform(put("/addresses/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedShouldReturnPage() throws Exception {

        ResultActions result = mockMvc.perform(get("/addresses").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnAddressDtoWhenIdExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/addresses/{id}", existingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].publicPlace").exists());
        result.andExpect(jsonPath("$.content[0].neighborhood").exists());
        result.andExpect(jsonPath("$.content[0].number").exists());
        result.andExpect(jsonPath("$.content[0].city").exists());
        result.andExpect(jsonPath("$.content[0].state").exists());
        result.andExpect(jsonPath("$.content[0].postalCode").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/addresses/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
