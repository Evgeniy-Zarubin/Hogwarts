package ru.hogwarts.school.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private static final String DIR_PATH = "avatars";

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Path path = Path.of(DIR_PATH);
        if (Files.notExists(path)){
            Files.createDirectories(path);
        }

        Student student = studentService.getStudent(studentId);
        if (student != null && file != null && !file.isEmpty() && file.getOriginalFilename() != null) {
            String fileExtension = getExtension(file.getOriginalFilename());
            if (!StringUtils.isBlank(fileExtension)) {
                Path filePath = Path.of(DIR_PATH, student.getName() + "_" + student.getId() + "." + fileExtension);
                file.transferTo(filePath);
                Avatar newAvatar = new Avatar();
                newAvatar.setFilePath(filePath.toString());
                newAvatar.setFileSize(file.getSize());
                newAvatar.setMediaType(file.getContentType());
                newAvatar.setData(file.getBytes());
                newAvatar.setStudent(student);
                avatarRepository.save(newAvatar);
            }
        }
    }

    @Override
    public Avatar getAvatarFromDB(Long studentId) {
        Student student = studentService.getStudent(studentId);
        return avatarRepository.findAvatarByStudent(student).orElseThrow(() -> new NotFoundException("Аватарка для студента " + student.getName() + "не найдена"));
    }

    @Override
    public byte[] getAvatarFromLocal(Long studentId) {
        Student student = studentService.getStudent(studentId);
        Avatar avatar = avatarRepository.findAvatarByStudent(student).orElseThrow(() -> new NotFoundException("Аватарка для студента " + student.getName() + "не найдена"));
        String filePath = avatar.getFilePath();
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return bufferedInputStream.readAllBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при получении аватара");
        }
    }

    private String getExtension (String originalPath) {
        if (StringUtils.isBlank(originalPath)) {
            return originalPath.substring(originalPath.lastIndexOf(",") + 1);
        } else return null;

    }

}
