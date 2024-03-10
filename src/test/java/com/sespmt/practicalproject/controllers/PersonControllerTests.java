package com.sespmt.practicalproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sespmt.practicalproject.dto.PersonDto;
import com.sespmt.practicalproject.services.PersonService;
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
public class PersonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PersonDto personDto;
    private PageImpl<PersonDto> page;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        personDto = Factory.createPersonDto();
        page = new PageImpl<>(List.of(personDto));

        when(personService.findAllPaged(any())).thenReturn(page);

        when(personService.findById(existingId)).thenReturn(page);
        when(personService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        when(personService.update(eq(existingId), any())).thenReturn(page);
        when(personService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        doNothing().when(personService).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(personService).delete(nonExistingId);
        doThrow(DatabaseException.class).when(personService).delete(dependentId);

        when(personService.insert(any())).thenReturn(page);

    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/persons/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/persons/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnPersonDtoCreated() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDto);

        ResultActions result =
                mockMvc.perform(post("/persons")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
        result.andExpect(jsonPath("$.content[0].rg").exists());
        result.andExpect(jsonPath("$.content[0].cpf").exists());
        result.andExpect(jsonPath("$.content[0].birthDate").exists());
        result.andExpect(jsonPath("$.content[0].phoneNumber").exists());
        result.andExpect(jsonPath("$.content[0].motherName").exists());
        result.andExpect(jsonPath("$.content[0].fatherName").exists());
        result.andExpect(jsonPath("$.content[0].addresses").exists());
    }

    @Test
    public void updateShouldReturnPersonDtoWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDto);

        ResultActions result =
                mockMvc.perform(put("/persons/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
        result.andExpect(jsonPath("$.content[0].rg").exists());
        result.andExpect(jsonPath("$.content[0].cpf").exists());
        result.andExpect(jsonPath("$.content[0].birthDate").exists());
        result.andExpect(jsonPath("$.content[0].phoneNumber").exists());
        result.andExpect(jsonPath("$.content[0].motherName").exists());
        result.andExpect(jsonPath("$.content[0].fatherName").exists());
        result.andExpect(jsonPath("$.content[0].addresses").exists());
    }

    @Test
    public void updateShouldReturnResourceNotFoundWhenIdDoesNotExist() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(personDto);

        ResultActions result =
                mockMvc.perform(put("/persons/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findAllPagedShouldReturnPage() throws Exception {

        ResultActions result = mockMvc.perform(get("/persons").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnPersonDtoWhenIdExists() throws Exception {

        ResultActions result = mockMvc.perform(get("/persons/{id}", existingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].id").exists());
        result.andExpect(jsonPath("$.content[0].name").exists());
        result.andExpect(jsonPath("$.content[0].rg").exists());
        result.andExpect(jsonPath("$.content[0].cpf").exists());
        result.andExpect(jsonPath("$.content[0].birthDate").exists());
        result.andExpect(jsonPath("$.content[0].phoneNumber").exists());
        result.andExpect(jsonPath("$.content[0].motherName").exists());
        result.andExpect(jsonPath("$.content[0].fatherName").exists());
        result.andExpect(jsonPath("$.content[0].addresses").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/persons/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
