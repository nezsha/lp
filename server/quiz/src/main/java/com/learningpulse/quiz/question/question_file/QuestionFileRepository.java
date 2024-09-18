package com.learningpulse.quiz.question.question_file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionFileRepository extends JpaRepository<QuestionFile, UUID> {

}
