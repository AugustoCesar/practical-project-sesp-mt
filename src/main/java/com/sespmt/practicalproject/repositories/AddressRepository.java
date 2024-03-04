package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
