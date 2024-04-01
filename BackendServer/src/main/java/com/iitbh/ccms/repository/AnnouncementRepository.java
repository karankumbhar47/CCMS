package com.iitbh.ccms.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.iitbh.ccms.model_db.AnnouncementDoc;

@Repository
public interface AnnouncementRepository extends MongoRepository<AnnouncementDoc, ObjectId> {
    public AnnouncementDoc findAnnouncementsById(Long id);
}
