package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.Complaint;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint, ObjectId> {
    Complaint findByComplaintId(String id);
}