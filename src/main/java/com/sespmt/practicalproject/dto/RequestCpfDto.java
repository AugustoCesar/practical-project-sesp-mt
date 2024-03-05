package com.sespmt.practicalproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

public class RequestCpfDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Size(min = 11, max = 11, message = "Deve conter 11 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    @CPF(message = "Número de CPF inválido")
    private String cpf;

    public RequestCpfDto() {
    }

    public RequestCpfDto(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
