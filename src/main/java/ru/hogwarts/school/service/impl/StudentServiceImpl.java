package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> repository = new HashMap<>();
    private long counter = 0L;

    @Override
    public Student addStudent(Student student) {
        student.setId(counter++);
        repository.put(counter, student);
        return student;
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        checkExistStudent(id);
        student.setId(id);
        repository.put(id, student);
        return student;
    }

    @Override
    public Student getStudent(Long id) {
        return repository.get(id);
    }

    @Override
    public void deleteStudent(Long id) {
        checkExistStudent(id);
        repository.remove(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return List.copyOf(repository.values());
    }

    private void checkExistStudent(Long id) {
        if (!repository.containsKey(id)) {
            throw new IllegalArgumentException("Студент с id" + id + "не найден");
        }
    }
}
