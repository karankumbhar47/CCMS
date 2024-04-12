package com.iitbh.ccms.model_db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Location")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDB {
    @Id
    private ObjectId objectId;
    private String zoneName;
    private List<String> buildings;
    private List<Category> categories;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        private String name;
        private List<String> emails;
    }

}
