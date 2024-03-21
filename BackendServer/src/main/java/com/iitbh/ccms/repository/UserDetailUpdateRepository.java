package com.iitbh.ccms.repository;


import com.iitbh.ccms.model_DB.UserDetailUpdate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface UserDetailUpdateRepository extends MongoRepository<UserDetailUpdate, ObjectId>{

    Optional<UserDetailUpdate> findUserDetailUpdateByUserId(int userId);

}