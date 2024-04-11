package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.LocationDB;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LocationRepository  extends MongoRepository<LocationDB, ObjectId> {
}