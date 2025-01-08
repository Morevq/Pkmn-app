package su.morevq.pkmn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import su.morevq.pkmn.models.student.Student;

import java.util.UUID;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "sur_name")
    private String surName;

    @Column
    private String group;

    public static StudentEntity fromModel(Student student) {
        return StudentEntity.builder()
                .firstName(student.getFirstName())
                .familyName(student.getPatronicName())
                .surName(student.getSurName())
                .group(student.getGroup())
                .build();
    }
}