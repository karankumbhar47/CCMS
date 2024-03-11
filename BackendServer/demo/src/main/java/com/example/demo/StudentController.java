package com.example.demo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent(){
        return new ResponseEntity<List<Student>>(studentService.getAllStudent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> getSingleStudent(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Student>>(studentService.getSingleStudent(id),HttpStatus.OK);
    }

    @GetMapping("/id/{studentId}")
    public ResponseEntity<Optional<Student>> getSingleStudent(@PathVariable String studentId) {
        return new ResponseEntity<Optional<Student>>(studentService.getSingleStudentByStudentId(studentId),HttpStatus.OK);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Optional<Student>> getSingleStudentByName(@PathVariable String name) {
        return new ResponseEntity<Optional<Student>>(studentService.getSingleStudentByName(name),HttpStatus.OK);
    }
}
