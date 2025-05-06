package ru.hogwarts.school.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        checkExistFaculty(id);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        checkExistFaculty(id);
        return facultyRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("Факультет с id %s не найден", id)));
    }

    @Override
    public void deleteFaculty(Long id) {
        checkExistFaculty(id);
        facultyRepository.deleteById(id);
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    private void checkExistFaculty(Long id){
        if (!facultyRepository.existsById(id)){
            throw new NotFoundException("Факультет с id" + id + "не найден");
        }
    }
}
