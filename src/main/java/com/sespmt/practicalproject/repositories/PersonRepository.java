package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

}
