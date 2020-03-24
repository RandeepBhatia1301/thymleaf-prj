package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    List<Avatar> findAllByOrderByCreatedAt();

}
