package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.WelcomeScreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WelcomeScreenRepository extends JpaRepository<WelcomeScreen, Integer> {

}
