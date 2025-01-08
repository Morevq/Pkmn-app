package su.morevq.pkmn.models.student;

import lombok.*;
import lombok.experimental.FieldDefaults;
import su.morevq.pkmn.entity.StudentEntity;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student implements Serializable {
    public static final long serialVersionUID = 1L;
    String firstName;
    String surName;
    String patronicName;
    String group;

    public static Student fromEntity(StudentEntity entity)
    {
        return Student.builder()
                .firstName(entity.getFirstName())
                .patronicName(entity.getFamilyName())
                .surName(entity.getSurName())
                .group(entity.getGroup())
                .build();
    }
}