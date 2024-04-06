package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.UserDetailUpdateDB;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeleteUserRepository extends MongoRepository<UserDetailUpdateDB, ObjectId> {

}
