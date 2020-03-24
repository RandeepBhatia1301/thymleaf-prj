package org.ril.hrss.repository;

import org.ril.hrss.model.content.poll.PollResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollResultRepository extends JpaRepository<PollResult, Long>, PollResultRepositoryCustom {
}
