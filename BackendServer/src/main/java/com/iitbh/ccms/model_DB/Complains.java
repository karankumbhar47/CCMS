package com.iitbh.ccms.model_DB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="complains")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complains {
    @Id
    ObjectId objectId;
    int complainId;
    int studentId;
    String complainType;
    String description;
    String status;
}
