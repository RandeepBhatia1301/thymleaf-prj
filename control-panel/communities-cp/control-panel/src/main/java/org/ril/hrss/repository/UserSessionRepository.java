package org.ril.hrss.repository;

import org.ril.hrss.model.content.quiz.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long>, UserSessionCustomRepository {

}
