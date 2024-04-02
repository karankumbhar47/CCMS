package com.iitbh.ccms.service;

import com.iitbh.ccms.model.UserDetailUpdate;
import com.iitbh.ccms.model_db.UserDetailUpdateDB;
import com.iitbh.ccms.repository.CreateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserService {
    private final CreateUserRepository createUserRepository;

    @Autowired
    public CreateUserService(CreateUserRepository createUserRepository){
        this.createUserRepository = createUserRepository;
    }

    public void createUser(UserDetailUpdate userDetailUpdate) {
        // Mapping UserDetailUpdate to UserDetailUpdateDB
        UserDetailUpdateDB userDetailUpdateDB = new UserDetailUpdateDB();
        userDetailUpdateDB.setUserId(userDetailUpdate.getUserId());
        userDetailUpdateDB.setUserName(userDetailUpdate.getUserName());
        userDetailUpdateDB.setRole(userDetailUpdate.getRole());
        userDetailUpdateDB.setEmail(userDetailUpdate.getEmail());
        userDetailUpdateDB.setStatus(userDetailUpdate.getStatus());
        userDetailUpdateDB.setDateRegistered(userDetailUpdate.getDateRegistered());

        // Saving the mapped object to the repository
        createUserRepository.save(userDetailUpdateDB);
    }
}
