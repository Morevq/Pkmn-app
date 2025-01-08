package su.morevq.pkmn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import su.morevq.pkmn.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository  extends JpaRepository<StudentEntity, UUID> {
    Optional<List<StudentEntity>> findStudentEntitiesByGroup(String group);
    Optional<StudentEntity> findStudentEntityByFirstNameAndFamilyNameAndSurName(String firstName, String familyName, String surName);
    Optional<StudentEntity> findStudentEntityByFirstNameAndFamilyNameAndSurNameAndGroup(String firstName, String familyName, String surName, String group);

}
