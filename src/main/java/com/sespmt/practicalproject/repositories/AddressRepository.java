package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("SELECT a FROM AddressEntity a WHERE LOWER(a.state) LIKE LOWER(CONCAT('%', LOWER(:state), '%')) " +
            "AND LOWER(a.city) LIKE LOWER(CONCAT('%', LOWER(:city), '%'))")
    List<AddressEntity> searchByCity(String state,
                                     String city);

}
