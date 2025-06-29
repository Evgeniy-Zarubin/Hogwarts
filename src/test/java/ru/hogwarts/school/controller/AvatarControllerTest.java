package ru.hogwarts.school.controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarControllerTest {

    @Mock
    private AvatarService avatarService;

    @InjectMocks
    private AvatarController avatarController;

    @Test
    void testUploadAvatar_Success() throws IOException {
        //Given
        Long studentId = 1L;
        MultipartFile file = new MockMultipartFile(
            "file",
            "avatar.jpeg",
            MediaType.IMAGE_JPEG_VALUE,
            "test image content".getBytes(StandardCharsets.UTF_8)
        );

        doNothing().when(avatarService).uploadAvatar(anyLong(), any(MultipartFile.class));

        //When
        avatarController.uploadAvatar(studentId, file);

        //Then
        verify(avatarService, times(1)).uploadAvatar(studentId, file);

    }

    @Test
    void testUploadAvatar_IOException() throws IOException {
        //Given
        Long studentId = 1L;
        MultipartFile file = new MockMultipartFile(
                "file",
                "avatar.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes(StandardCharsets.UTF_8)
        );

        doThrow(new IOException("file upload failed")).when(avatarService).uploadAvatar(anyLong(),any(MultipartFile.class));

        //When & Then
        assertThrows(IOException.class, () -> avatarService.uploadAvatar(studentId, file));

    }

    @Test
    void testGetAvatarFromDb_Success(){
        //Given
        Long studentId = 1L;
        Avatar avatar = new Avatar();
        avatar.setMediaType(MediaType.IMAGE_JPEG_VALUE);
        avatar.setData("test image content".getBytes(StandardCharsets.UTF_8));

        when(avatarService.getAvatarFromDB(studentId)).thenReturn(avatar);

        //When
        ResponseEntity<byte[]> response = avatarController.getAvatarFromDB(studentId);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.IMAGE_JPEG_VALUE, response.getHeaders().getContentType().toString());
        assertArrayEquals("test image content".getBytes(StandardCharsets.UTF_8), response.getBody());

    }

    @Test
    void testGetAvatarLocalStorage_Success(){
        //Given
        Long studentId = 1L;
        byte[] expectedData = "test data".getBytes(StandardCharsets.UTF_8);

        when(avatarService.getAvatarFromLocal(studentId)).thenReturn(expectedData);

        //When
        byte[] response = avatarController.getAvatarFromLocal(studentId);

        //Then
        assertArrayEquals(expectedData, response);
    }

}
