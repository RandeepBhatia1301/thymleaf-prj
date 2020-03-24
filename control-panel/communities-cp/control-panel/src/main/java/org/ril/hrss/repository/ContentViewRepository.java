package org.ril.hrss.repository;

import org.ril.hrss.model.ContentView;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentViewRepository extends MongoRepository<ContentView, String>, ContentViewCustomRepository {


}
