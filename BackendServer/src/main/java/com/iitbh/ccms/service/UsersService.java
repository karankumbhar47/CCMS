package com.iitbh.ccms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model.UserDetails;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public List<UserDetailsDB> getAllUserDetail(int page, int size) {
        // Fetch user details for the specified page and size
        Page<UserDetailsDB> userDetailsPage = usersRepository.findAll(PageRequest.of(page - 1, size));
        return userDetailsPage.getContent();
    }

    public int getTotalElements() {
        // Get total number of user details

        return (int) usersRepository.count();
    }

    public int getTotalPages(int size) {
        // Calculate total number of pages based on page size and total elements
        long totalElements = getTotalElements();
        return (int) Math.ceil((double) totalElements / size);
    }

    public void updateUserDetails(String userId, UserDetails userDetailsUpdateRequest) {
        // Find the user by userId
        Optional<UserDetailsDB> optionalUser = usersRepository.findUsersByUserName(userId);

        if (optionalUser.isPresent()) {
            UserDetailsDB existingUser = optionalUser.get();
            // Update the fields of existing user with the updated values
            existingUser.setUserName(userDetailsUpdateRequest.getUserName());
            existingUser.setRoles(
                    userDetailsUpdateRequest.getRoles()
                            .stream()
                            .map(role -> role.toString())
                            .collect(Collectors.toCollection(ArrayList::new))

            );
            existingUser.setEmail(userDetailsUpdateRequest.getEmail());
            existingUser.setStatus(userDetailsUpdateRequest.getStatus().toString());
            existingUser.setDateRegistered(userDetailsUpdateRequest.getDateRegistered());
            // Save the updated user details
            usersRepository.save(existingUser);
            System.out.println("User details updated successfully.");
        } else {
            System.out.println("User with userId " + userId + " not found.");
        }
    }

    public void deleteUserById(int userId) {
        usersRepository.delete(usersRepository.findByUserId(userId));
    }

    public void createUser(UserDetailsDB userDetailUpdate) {
        // Mapping UserDetailUpdate to UserDetailUpdateDB
        UserDetailsDB userDetailDB = new UserDetailsDB();
        userDetailDB.setUserId(userDetailUpdate.getUserId());
        userDetailDB.setUserName(userDetailUpdate.getUserName());
        userDetailDB.setRoles(userDetailUpdate.getRoles());
        userDetailDB.setEmail(userDetailUpdate.getEmail());
        userDetailDB.setStatus(userDetailUpdate.getStatus());
        userDetailDB.setDateRegistered(userDetailUpdate.getDateRegistered());

        // Saving the mapped object to the repository
        usersRepository.save(userDetailDB);
    }

    public Optional<UserDetailsDB> singleUser(String email) {
        return usersRepository.findUsersByUserName(email);
    }
}