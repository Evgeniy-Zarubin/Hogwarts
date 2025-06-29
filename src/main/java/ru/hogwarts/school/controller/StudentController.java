package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "Контроллер для работы со студентами", description = "Предоставляет CURD по студентам")
public class StudentController {

    private final StudentService studentService;

    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable(name = "id") Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable(name = "id") Long id){
        return studentService.getStudent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable(name = "id") Long id){
        studentService.deleteStudent(id);
    }

    @GetMapping("/all")
    public List<Student> getAllStudent(){
        return studentService.getAllStudent();
    }

    @GetMapping("/age-range")
    public List<Student> getStudentsByAgeRange(@RequestParam(value="min") int min, @RequestParam(value="max") int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCountOfStudents() {
        int totalStudents = studentRepository.countStudents();
        return ResponseEntity.ok(totalStudents);
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        double avgAge = studentRepository.averageAge();
        return ResponseEntity.ok(avgAge);
    }

    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        List<Student> lastFiveStudents = studentRepository.findLastFiveStudents();
        return ResponseEntity.ok(lastFiveStudents);
    }

}
