package com.sespmt.practicalproject.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String publicPlace;

    @Column(nullable = false, length = 50)
    private String neighborhood;
    private Integer number;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 2)
    private String state;

    @Column(nullable = false, length = 100)
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private PersonEntity person;

    public AddressEntity() {
    }

    public AddressEntity(Long id,
                         String publicPlace,
                         String neighborhood,
                         Integer number,
                         String city,
                         String state,
                         String postalCode,
                         PersonEntity person) {
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

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity address = (AddressEntity) o;

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
}
