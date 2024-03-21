package com.iitbh.ccms.repository;

import com.iitbh.ccms.model_db.Student;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, ObjectId> {
    Optional<Student> findStudentByName(String name);
    Optional<Student> findStudentByStudentId(int id);

    Optional<Student> findStudentByAgeIs(int age);



}
