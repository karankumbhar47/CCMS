package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.UserDetailUpdateDB;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CreateUserRepository extends MongoRepository<UserDetailUpdateDB, ObjectId> {
    UserDetailUpdateDB findByUserId(int id);

}
