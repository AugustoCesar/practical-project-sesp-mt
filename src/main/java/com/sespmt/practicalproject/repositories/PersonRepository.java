package com.sespmt.practicalproject.repositories;

import com.sespmt.practicalproject.entities.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    Page<PersonEntity> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    Page<PersonEntity> findByCpf(String cpf, Pageable pageable);

    Optional<PersonEntity> findByMotherNameIgnoreCase(String motherName);

    @Query("SELECT p FROM PersonEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', LOWER(:name), '%')) " +
            "AND (p.birthDate = :birthDate OR :birthDate IS NULL) " +
            "AND LOWER(p.motherName) LIKE LOWER(CONCAT('%', LOWER(:motherName), '%'))")
    Page<PersonEntity> searchFiltered(String name,
                                      LocalDate birthDate,
                                      String motherName,
                                      Pageable pageable);

    List<PersonEntity> findByIdIn(List<Long> idList);
}
