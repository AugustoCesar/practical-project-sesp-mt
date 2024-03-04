package com.sespmt.practicalproject.dto;

import java.io.Serializable;
import java.util.Objects;

public class AddressDto implements Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;
    private String publicPlace;
    private String neighborhood;
    private Integer number;
    private String city;
    private String state;
    private String postalCode;
    private PersonDto personDto;

    public AddressDto() {
    }

    public AddressDto(Long id,
                      String publicPlace,
                      String neighborhood,
                      Integer number,
                      String city,
                      String state,
                      String postalCode) {
        this.id = id;
        this.publicPlace = publicPlace;
        this.neighborhood = neighborhood;
        this.number = number;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
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
                ", personDto=" + personDto +
                '}';
    }
}
