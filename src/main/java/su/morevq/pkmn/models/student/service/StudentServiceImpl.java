package su.morevq.pkmn.models.student.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.morevq.pkmn.models.student.Student;
import su.morevq.pkmn.dao.StudentDao;
import su.morevq.pkmn.entity.StudentEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentDao studentDao;

    @Override
    public List<Student> getStudentsByGroup(String group) {
        List<StudentEntity> s = studentDao.getStudentsByGroup(group);
        if (s.isEmpty()){
            throw new RuntimeException("Students with group " + group + " not found");
        }
        return s.stream().map(Student::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Student getStudentByFullName(String familyName, String firstName, String surName) {
        return Student.fromEntity(studentDao.getStudentByFullName(familyName, firstName, surName));
    }

    @Override
    public Student getStudentByFullName(String fullName) {
        String[] arr = fullName.split(" ");
        if (arr.length < 3){
            throw new RuntimeException("Invalid full name");
        }
        return getStudentByFullName(arr[0], arr[1], arr[2]);
    }

    @Override
    public Optional<StudentEntity> getExactStudent(String firstName, String familyName, String surName, String group) {
        return studentDao.getExactStudent(firstName, familyName, surName, group);
    }

    @Transactional
    @Override
    public StudentEntity saveStudent(Student student) {
        if (studentDao.getExactStudent(
                student.getFirstName(),
                student.getPatronicName(),
                student.getSurName(),
                student.getGroup()
        ).isPresent()) {
            throw new RuntimeException("Student already exists");
        }

        StudentEntity studentEntity = StudentEntity.fromModel(student);
        return studentDao.saveStudent(studentEntity);
    }

    @Override
    public List<Student> getAllStudents() {
        List<StudentEntity> students = studentDao.getAllStudents();
        if (!students.isEmpty()){
            return students.stream().map(Student::fromEntity).collect(Collectors.toList());
        }
        throw new RuntimeException("No students found");
    }
}
