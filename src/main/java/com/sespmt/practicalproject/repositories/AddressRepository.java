package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

}
