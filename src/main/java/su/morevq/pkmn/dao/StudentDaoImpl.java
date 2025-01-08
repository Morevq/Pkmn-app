package su.morevq.pkmn.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import su.morevq.pkmn.entity.StudentEntity;
import su.morevq.pkmn.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao{
    private final StudentRepository studentRepository;

    @Override
    public List<StudentEntity> getStudentsByGroup(String group) {
        return studentRepository.findStudentEntitiesByGroup(group).orElseThrow(
                () -> new RuntimeException("Students with group " + group + " not found")
        );
    }

    @Override
    public StudentEntity getStudentByFullName(String familyName, String firstName, String surName) {
        return studentRepository.findStudentEntityByFirstNameAndFamilyNameAndSurName(firstName, familyName, surName).orElseThrow(
                () -> new RuntimeException("Student with full name " + firstName + " " + familyName + " " + surName + " not found")
        );
    }

    @Override
    public StudentEntity saveStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group) {
        return studentRepository.findStudentEntityByFirstNameAndFamilyNameAndSurNameAndGroup(firstName, familyName, surName, group);
    }

    @Override
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }
}
