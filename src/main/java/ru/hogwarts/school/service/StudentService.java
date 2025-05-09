package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;
import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student updateStudent(Long id, Student student);

    Student getStudent(Long id);

    void deleteStudent(Long id);

    List<Student> getAllStudent();

    List<Student> findByAgeBetween(int minAge, int maxAge);

}
