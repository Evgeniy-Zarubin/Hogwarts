package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import java.util.List;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Faculty updateFaculty(Long id, Faculty faculty);

    Faculty getFaculty(Long id);

    void deleteFaculty(Long id);

    List<Faculty>getAllFaculty();


}
