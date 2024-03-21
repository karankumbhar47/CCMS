package com.iitbh.ccms.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="User-Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDB {
    // mention same variable names
    @Id
    ObjectId objectId;
    int userId;
    String userName;
    String role;
    String email;
    String status;
    String dateRegistered;
}

