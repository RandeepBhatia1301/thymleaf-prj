package org.ril.hrss.repository;

import org.ril.hrss.model.content.poll.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long>, PollRepositoryCustom {
    List<Poll> findByIdIn(List<Long> ids);
}
