package com.iitbh.ccms.model_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDB {
    @Id
    ObjectId objectId;

    String fileId;
    String imageData;
}
