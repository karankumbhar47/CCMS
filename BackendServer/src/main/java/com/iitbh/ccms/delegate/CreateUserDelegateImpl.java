package com.iitbh.ccms.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.CreateUserApiDelegate;
import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.service.UsersService;

@Service
public class CreateUserDelegateImpl implements CreateUserApiDelegate {
    private final UsersService usersService;

    @Autowired
    public CreateUserDelegateImpl(UsersService createUserService) {
        this.usersService = createUserService;
    }

    @Override
    public ResponseEntity<Void> createUser(UserInfo userInfoUpdate) {
        UserDetailsDB newUserDetails = new UserDetailsDB(userInfoUpdate);
        usersService.createUser(newUserDetails);
        return ResponseEntity.ok().build();
    }
}
