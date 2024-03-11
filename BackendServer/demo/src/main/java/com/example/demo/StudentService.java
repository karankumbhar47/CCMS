package com.example.demo;

import org.apache.commons.logging.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudent(){
        System.out.println(studentRepository.findStudentByName("John"));
        return studentRepository.findAll();
    }

    public Optional<Student> getSingleStudent(ObjectId objectId){
        return studentRepository.findById(objectId);
    }

    public Optional<Student> getSingleStudentByName(String name){
        return studentRepository.findStudentByName(name);
    }

    public Optional<Student> getSingleStudentByStudentId(String Id){
        return studentRepository.findStudentByStudentId(Integer.parseInt(Id));
    }
}
