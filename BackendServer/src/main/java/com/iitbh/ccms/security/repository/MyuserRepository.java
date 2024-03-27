package com.iitbh.ccms.security.repository;

import com.iitbh.ccms.security.models.Myuser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MyuserRepository extends MongoRepository<Myuser, ObjectId> {

    Optional<Myuser> findMyuserByEmail(String email);
}
