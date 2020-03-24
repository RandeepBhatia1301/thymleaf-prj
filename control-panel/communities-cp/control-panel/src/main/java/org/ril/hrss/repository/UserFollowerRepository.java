package org.ril.hrss.repository;
import org.ril.hrss.model.UserFollow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserFollowerRepository extends MongoRepository<UserFollow, String> {

    public Integer deleteAllByUserIdOrFollowerId(Long userId,Long followerId);

}
