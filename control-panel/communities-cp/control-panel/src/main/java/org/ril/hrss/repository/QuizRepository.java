package org.ril.hrss.repository;


import org.ril.hrss.model.content.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface QuizRepository extends JpaRepository<Quiz, Long>, QuizCustomRepository {
    List<Quiz> findByIdIn(List<Long> ids);
}

