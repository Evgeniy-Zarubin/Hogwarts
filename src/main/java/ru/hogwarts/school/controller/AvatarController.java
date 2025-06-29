package ru.hogwarts.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;

import java.awt.print.Pageable;
import java.io.IOException;


@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    private final AvatarRepository avatarRepository;

    public AvatarController(AvatarService avatarService, AvatarRepository avatarRepository) {
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }

    @PostMapping(value = "/upload/{student_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@PathVariable("studentId") Long studentId, @RequestBody MultipartFile file) throws IOException {
        avatarService.uploadAvatar(studentId, file);
    }

    @GetMapping("/from-db")
    public ResponseEntity<byte[]> getAvatarFromDB(@RequestParam("studentId") Long studentId) {
        Avatar avatar = avatarService.getAvatarFromDB(studentId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(avatar.getMediaType())).body(avatar.getData());
    }

    @GetMapping(value = "/from-local", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatarFromLocal(@RequestParam("studentId") Long studentId) {
        return avatarService.getAvatarFromLocal(studentId);
    }

    @GetMapping("")
    public ResponseEntity<Page<Avatar>> getAvatars(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {


        Pageable paging = (Pageable) PageRequest.of(pageNumber, pageSize);


        Page<Avatar> avatarsPage = avatarRepository.findAll(paging);

        return ResponseEntity.ok(avatarsPage);
    }

}
