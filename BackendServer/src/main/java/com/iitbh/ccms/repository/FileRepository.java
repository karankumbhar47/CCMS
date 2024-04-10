package com.iitbh.ccms.repository;


import com.iitbh.ccms.model_db.FileDB;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileDB, ObjectId> {
    FileDB getFileDBByFileId(String fileId);
//    List<String> findAllFileIds();
}
