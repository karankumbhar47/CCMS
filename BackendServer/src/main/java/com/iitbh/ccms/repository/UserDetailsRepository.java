package com.iitbh.ccms.repository;

import com.iitbh.ccms.model.UserDetailsDB;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetailsDB, ObjectId> {
}
