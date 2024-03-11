package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    // mention same variable names
    @Id
    ObjectId objectId;
    int studentId;
    String name;
    String grade;
    int age;
}
