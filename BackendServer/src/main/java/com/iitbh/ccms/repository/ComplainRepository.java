package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.Complains;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplainRepository extends MongoRepository<Complains, ObjectId> {

    public Complains findComplainsByComplainId(String complainId);

}


