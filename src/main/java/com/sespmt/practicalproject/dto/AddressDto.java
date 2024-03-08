package com.sespmt.practicalproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 1, max = 200, message = "Deve conter entre 1 e 200 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    private String publicPlace;

    @Size(min = 1, max = 50, message = "Deve conter entre 1 e 50 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    private String neighborhood;

    private Integer number;

    @Size(min = 1, max = 50, message = "Deve conter entre 1 e 50 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    private String city;

    @Size(min = 2, max = 2, message = "Deve conter 2 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    private String state;

    @Size(min = 1, max = 100, message = "Deve conter entre 1 e 100 caracteres válidos")
    @NotBlank(message = "Campo requerido")
    private String postalCode;

    private PersonDto person;

    private Long personId;

    public AddressDto() {
    }

    public AddressDto(Long id,
                      String publicPlace,
                      String neighborhood,
                      Integer number,
                      String city,
                      String state,
                      String postalCode,
                      PersonDto person) {
        this.id = id;
        this.publicPlace = publicPlace;
        this.neighborhood = neighborhood;
        this.number = number;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonIgnore
    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressDto address = (AddressDto) o;

        if (!Objects.equals(id, address.id)) return false;
        if (!Objects.equals(publicPlace, address.publicPlace)) return false;
        if (!Objects.equals(neighborhood, address.neighborhood))
            return false;
        if (!Objects.equals(number, address.number)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(state, address.state)) return false;
        return Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (publicPlace != null ? publicPlace.hashCode() : 0);
        result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "id=" + id +
                ", publicPlace='" + publicPlace + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
