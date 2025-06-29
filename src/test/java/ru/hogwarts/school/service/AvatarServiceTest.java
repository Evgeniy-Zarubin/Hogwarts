package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @Test
    void testUploadAvatar_Success() throws IOException {
        //Given
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("Evgeniy");

        MultipartFile file = new MockMultipartFile(
                "file",
                "avatar.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes(StandardCharsets.UTF_8)
        );

        when(studentService.getStudent(studentId)).thenReturn(student);
        when(avatarRepository.save(any(Avatar.class))).thenReturn(new Avatar());

        //When
        avatarService.uploadAvatar(studentId, file);

        //Then

        verify(avatarRepository, times(1)).save(any(Avatar.class));

    }

    @Test
    void testUploadAvatar_StudentNotFound(){
        //Given
        Long studentId = 1L;

        MultipartFile file = new MockMultipartFile(
                "file",
                "avatar.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes(StandardCharsets.UTF_8)
        );

        when(studentService.getStudent(studentId)).thenReturn(null);

        //When & Then
        assertDoesNotThrow(() -> avatarService.uploadAvatar(studentId,file));
        verify(avatarRepository, never()).save(any(Avatar.class));

    }

    @Test
    void testGetAvatarFromDB_Success(){
        //Given
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        Avatar avatar = new Avatar();
        avatar.setStudent(student);

        when(studentService.getStudent(studentId)).thenReturn(student);
        when(avatarRepository.findAvatarByStudent(student)).thenReturn(Optional.of(avatar));

        //When
        Avatar result = avatarService.getAvatarFromDB(studentId);

        assertEquals(avatar,result);
        assertEquals(student, result.getStudent());
    }

    @Test
    void testGetAvatarFromDB_NotFound(){
        //Given
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("Evgeniy");

        when(studentService.getStudent(studentId)).thenReturn(student);
        when(avatarRepository.findAvatarByStudent(student)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(NotFoundException.class, () -> avatarService.getAvatarFromDB(studentId));
    }
}
