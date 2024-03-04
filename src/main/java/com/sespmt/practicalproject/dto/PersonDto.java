package com.sespmt.practicalproject.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class PersonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String rg;
    private String cpf;
    private LocalDate birthDate;
    private String phoneNumber;
    private String motherName;
    private String fatherName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AddressDto address;

    public PersonDto() {
    }

    public PersonDto(Long id,
                     String name,
                     String rg,
                     String cpf,
                     LocalDate birthDate,
                     String phoneNumber,
                     String motherName,
                     String fatherName,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.rg = rg;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.motherName = motherName;
        this.fatherName = fatherName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDto person = (PersonDto) o;

        if (!Objects.equals(id, person.id)) return false;
        if (!Objects.equals(name, person.name)) return false;
        if (!Objects.equals(rg, person.rg)) return false;
        if (!Objects.equals(cpf, person.cpf)) return false;
        if (!Objects.equals(birthDate, person.birthDate)) return false;
        if (!Objects.equals(phoneNumber, person.phoneNumber)) return false;
        if (!Objects.equals(motherName, person.motherName)) return false;
        if (!Objects.equals(fatherName, person.fatherName)) return false;
        if (!Objects.equals(createdAt, person.createdAt)) return false;
        return Objects.equals(updatedAt, person.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rg != null ? rg.hashCode() : 0);
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (motherName != null ? motherName.hashCode() : 0);
        result = 31 * result + (fatherName != null ? fatherName.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rg='" + rg + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", motherName='" + motherName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", addressDto=" + address +
                '}';
    }
}
