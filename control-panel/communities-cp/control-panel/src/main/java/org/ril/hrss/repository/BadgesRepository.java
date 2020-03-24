package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgesRepository extends JpaRepository<Badge, Integer> {
}
