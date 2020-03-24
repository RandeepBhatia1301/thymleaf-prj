package org.ril.hrss.repository;

import org.ril.hrss.model.gamification.AvatarImageMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvatarImageRepository extends JpaRepository<AvatarImageMaster, Integer> {
    List<AvatarImageMaster> findAllByAvatarIdAndGender(@Param("avatarId") Integer avatarId, @Param("gender") Integer gender);

    Integer countAllByAvatarId(@Param(value = "avatarId") Integer avatarId);
}
