package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.UserDetailsDB;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<UserDetailsDB, ObjectId> {
    Optional<UserDetailsDB> findUsersByUserName(String userId);
    Optional<UserDetailsDB> findUsersByEmail(String email);
    UserDetailsDB findByUserId(String userId);
    UserDetailsDB findByUserName(String userId);
}
