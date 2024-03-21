package com.iitbh.ccms.service;

import com.iitbh.ccms.model_DB.UserDetailUpdate;
import com.iitbh.ccms.model.UserDetailUpdateRequest;
import com.iitbh.ccms.repository.UserDetailUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailUpdateService {

    @Autowired
    private UserDetailUpdateRepository userDetailUpdateRepository;

    public void updateUserDetails(int userId, UserDetailUpdateRequest userDetailUpdateRequest) {
        // Find the user by userId
        Optional<UserDetailUpdate> optionalUser = userDetailUpdateRepository.findUserDetailUpdateByUserId(userId);

        if (optionalUser.isPresent()) {
            UserDetailUpdate existingUser = optionalUser.get();
            // Update the fields of existing user with the updated values
            existingUser.setUserName(userDetailUpdateRequest.getUserName());
            existingUser.setRole(userDetailUpdateRequest.getRole());
            existingUser.setEmail(userDetailUpdateRequest.getEmail());
            existingUser.setStatus(userDetailUpdateRequest.getStatus());
            existingUser.setDateRegistered(userDetailUpdateRequest.getDateRegistered());

            // Save the updated user details
            userDetailUpdateRepository.save(existingUser);
            System.out.println("User details updated successfully.");
        } else {
            System.out.println("User with userId " + userId + " not found.");
        }
    }
}
