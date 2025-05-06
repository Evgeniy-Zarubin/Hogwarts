package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import javax.naming.Name;
import java.util.List;

@RestController
@RequestMapping("/faculties")
@Tag(name = "Контроллер для работы с факультетами", description = "Предоставляет CURD по факультетам")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable(name = "id") Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @GetMapping("/{id}")
    public Faculty getFaculty(@PathVariable(name = "id") Long id){
        return facultyService.getFaculty(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable(name = "id") Long id){
        facultyService.deleteFaculty(id);
    }

    @GetMapping("/all")
    public List<Faculty> getAllFaculty(){
        return facultyService.getAllFaculty();
    }
}
