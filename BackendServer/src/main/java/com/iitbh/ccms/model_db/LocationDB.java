package com.iitbh.ccms.model_db;

import com.iitbh.ccms.model.Category;
import com.iitbh.ccms.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Location convertToLocation(){
        Location location = new Location();
        location.setZoneName(this.getZoneName());
        location.setBuildings(this.getBuildings());
        List<com.iitbh.ccms.model.Category> list = new ArrayList<>();
        for(LocationDB.Category category: this.categories){
            com.iitbh.ccms.model.Category returnCategory = new com.iitbh.ccms.model.Category();
            returnCategory.setEmails(category.getEmails());
            returnCategory.setName(category.getName());
            list.add(returnCategory);
        }
        location.setCategories(list);
        return location;
    }

}
