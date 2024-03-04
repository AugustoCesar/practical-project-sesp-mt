package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
