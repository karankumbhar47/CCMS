package com.iitbh.ccms.model_db;

import java.time.LocalDate;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection="UserDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDB {
    @Id
    private ObjectId objectId;
    private String userId;
    private String userName;
    private ArrayList<String> roles;
    private String email;
    private String status;
    private LocalDate dateRegistered;

    private String name;
    private String password;

    public void addRole(String role){
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
}

