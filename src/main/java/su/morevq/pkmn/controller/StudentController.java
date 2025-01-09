package su.morevq.pkmn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.morevq.pkmn.models.student.Student;
import su.morevq.pkmn.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/group/{group}")
    public ResponseEntity<List<Student>> getStudentsByGroup(@PathVariable String group) {
        return ResponseEntity.ok(studentService.getStudentsByGroup(group));
    }

    @PostMapping("")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return ResponseEntity.ok(Student.fromEntity(studentService.saveStudent(student)));
    }
}
