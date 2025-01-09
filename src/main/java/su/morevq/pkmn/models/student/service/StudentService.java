package su.morevq.pkmn.models.student.service;

import su.morevq.pkmn.models.student.Student;
import su.morevq.pkmn.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public List<Student> getStudentsByGroup(String group);
    public Student getStudentByFullName(String familyName, String firstName, String surName);
    public Student getStudentByFullName(String fullName);
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group);
    public StudentEntity saveStudent(Student student);

    List<Student> getAllStudents();
}
