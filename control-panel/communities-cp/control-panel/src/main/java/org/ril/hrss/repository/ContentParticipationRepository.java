package org.ril.hrss.repository;

import org.ril.hrss.model.ContentParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentParticipationRepository extends JpaRepository<ContentParticipant, Long>, ContentParticipationRepositoryCustom {
    ContentParticipant findContentParticipantById(Long Id);
}
