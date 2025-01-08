package su.morevq.pkmn.dao;

import su.morevq.pkmn.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    public List<StudentEntity> getStudentsByGroup(String group);
    public StudentEntity getStudentByFullName(String familyName, String firstName, String surName);
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group);
    public StudentEntity saveStudent(StudentEntity student);
    public List<StudentEntity> getAllStudents();
}
