package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> repository = new HashMap<>();
    private long counter = 0L;

    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(counter++);
        repository.put(counter,faculty);
        return faculty;
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty faculty) {
        checkExistFaculty(id);
        faculty.setId(id);
        repository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return repository.get(id);
    }

    @Override
    public void deleteFaculty(Long id) {
        checkExistFaculty(id);
        repository.remove(id);
    }

    @Override
    public List<Faculty> getAllFaculty() {
        return List.copyOf(repository.values());
    }

    private void checkExistFaculty(Long id){
        if (!repository.containsKey(id)){
            throw new IllegalArgumentException("Факультет с id" + id + "не найден");
        }
    }
}
