package com.iitbh.ccms.delegate;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    public ResponseEntity<Void> createUser(UserInfo userDetailsUpdate) {
        UserDetailsDB newUserDetails = new UserDetailsDB();

        newUserDetails.setUserId(userDetailsUpdate.getUserId().toString());
        newUserDetails.setUserName(userDetailsUpdate.getUserName());
        newUserDetails.setEmail(userDetailsUpdate.getEmail());
        newUserDetails.setRoles(
                userDetailsUpdate.getRoles()
                .stream()
                .map(role -> role.toString())
                .collect(Collectors.toCollection(ArrayList::new))
                );
        newUserDetails.setStatus(userDetailsUpdate.getStatus().toString());
        newUserDetails.setDateRegistered(userDetailsUpdate.getDateRegistered());

        usersService.createUser(newUserDetails);
        return ResponseEntity.ok().build();
    }
}
