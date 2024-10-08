package com.learningpulse.classroom;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private static final Logger logger = LoggerFactory.getLogger(ClassroomService.class);
    private final ClassRoomRepository repo;
    private final Random random = new Random();

    public List<Classroom> findAll() {
        return repo.findAll();
    }

    // TODO add param: UUID creator
    public Classroom createClassroom(String name, UUID createdBy) {
        // TODO create chat for classroom

        String joinCode = generateJoinCode(4);
        new Classroom();
        Classroom classroom = Classroom.builder().joinCode(joinCode).name(name).createdBy(createdBy).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("join_code", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<Classroom> classroomExample = Example.of(classroom, matcher);

        while (repo.exists(classroomExample)) {
            joinCode = generateJoinCode(4);
            new Classroom();
            classroom = Classroom.builder().joinCode(joinCode).name(name).createdBy(createdBy).build();
        }
        return repo.save(classroom);
    }

    public void updateClassroom(Classroom Classroom_data) {
        repo.findById(Classroom_data.getId())
                .orElseThrow(() -> new RuntimeException("No Classroom under this id: " + Classroom_data.getId()));
        repo.save(Classroom_data);
    }

    public Classroom findByID(UUID uuid) {
        // FIXME handle unwrap errors
        return repo.findById(uuid).get();
    }

    public void delete(UUID id) {
        // FIXME handle errors
        repo.findById(id);
    }

    public void add_member(UUID classroom, UUID user) {
        Classroom classroom_model = repo.findById(classroom)
                .orElseThrow(() -> new RuntimeException("No Classroom under this id: " + classroom));
        repo.save(classroom_model);

    }

    private String generateJoinCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString().toLowerCase();

    }
}