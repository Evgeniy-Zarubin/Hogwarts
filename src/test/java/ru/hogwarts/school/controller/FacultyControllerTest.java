package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class FacultyControllerTest {

    @Mock
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;


    @Test
    void test_AddFaculty_Success() {
        //Given
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(1L);
        expectedFaculty.setName("Гриффиндор");

        when(facultyController.addFaculty(expectedFaculty)).thenReturn(expectedFaculty);

        //When
        Faculty result = facultyService.addFaculty(expectedFaculty);

        assertEquals(expectedFaculty, result);
    }

    @Test
    void test_UpdateFaculty_Success() throws Exception {
            // Given
            long facultyId = 1L;
            Faculty updatedFaculty = new Faculty();
            updatedFaculty.setId(facultyId);
            updatedFaculty.setName("Гриффиндор");


            when(facultyService.updateFaculty(eq(facultyId), any(Faculty.class))).thenReturn(updatedFaculty);

            // When
            Faculty result = facultyController.updateFaculty(1L, updatedFaculty);

            assertNotEquals(updatedFaculty, result);

        }


    @Test
    void test_GetFaculty_Success() {
        //Given
        long facultyId = 1L;
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(facultyId);
        expectedFaculty.setName("Гриффиндор");

        when(facultyService.getFaculty(facultyId)).thenReturn(expectedFaculty);

        //When
        Faculty result = facultyController.getFaculty(facultyId);

        assertEquals(expectedFaculty, result);

    }

    @Test
    void test_DeleteFaculty_Success() {
        //Given
        long facultyId = 1L;
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(facultyId);
        expectedFaculty.setName("Гриффиндор");

        when(facultyService.deleteFaculty(facultyId)).thenReturn(null);

        //When
        Faculty result = facultyController.deleteFaculty(facultyId);

        assertEquals(expectedFaculty, 0);


    }

    @Test
    void test_FindFaculties_Success() {
        // Given
        long facultyId = 1L;
        Faculty expectedFaculty = new Faculty();
        expectedFaculty.setId(facultyId);
        expectedFaculty.setName("Гриффиндор");

        long facultyId2 = 2L;
        Faculty expectedFaculty2 = new Faculty();
        expectedFaculty2.setId(facultyId2);
        expectedFaculty2.setName("Слизерин");

        List<Faculty> expectedList = Arrays.asList(expectedFaculty, expectedFaculty2);

        when(facultyService.getAllFaculty()).thenReturn(List<Faculty> expectedList);

        //When


        assertThat(actualList.size()).isEqualTo(2);
        assertThat(actualList.contains(expectedFaculty));
        assertThat(actualList.contains(expectedFaculty2));


    }
}