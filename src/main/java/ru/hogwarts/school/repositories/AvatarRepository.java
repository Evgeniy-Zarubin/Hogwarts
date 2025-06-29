package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional <Avatar> findAvatarByStudent(Student student);

    Page<Avatar> findAll(Pageable pageable);

}
