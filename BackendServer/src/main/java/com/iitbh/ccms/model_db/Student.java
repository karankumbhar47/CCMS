package com.iitbh.ccms.model_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
