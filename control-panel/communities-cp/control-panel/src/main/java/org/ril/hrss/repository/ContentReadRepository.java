package org.ril.hrss.repository;

import org.ril.hrss.model.ContentRead;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentReadRepository extends MongoRepository<ContentRead, String>, ContentReadCustomRepository {

}
