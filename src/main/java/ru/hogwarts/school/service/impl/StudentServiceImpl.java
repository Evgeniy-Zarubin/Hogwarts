package ru.hogwarts.school.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        checkExistStudent(id);
        return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        checkExistStudent(id);
        return studentRepository.getReferenceById(id);
    }

    @Override
    public void deleteStudent(Long id) {
        checkExistStudent(id);
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    private void checkExistStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Студент с id" + id + "не найден");
        }
    }
}
