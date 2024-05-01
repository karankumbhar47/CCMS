package com.iitbh.ccms.delegate;

import java.util.Random;

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
    private static String allChars = "abcdefghijklmnopqrstuvwxyz1234567890!@?";

    @Autowired
    public CreateUserDelegateImpl(UsersService createUserService) {
        this.usersService = createUserService;
    }

    @Override
    public ResponseEntity<Void> createUser(UserInfo userInfoUpdate) {
        UserDetailsDB newUserDetails = new UserDetailsDB(userInfoUpdate);

        newUserDetails.setPassword(generatePassword(8));
        usersService.createUser(newUserDetails);
        return ResponseEntity.ok().build();
    }

    private String generatePassword(int length) {
        StringBuilder passwordBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allChars.length());
            passwordBuilder.append(allChars.charAt(randomIndex));
        }
        return passwordBuilder.toString();
    }
}
