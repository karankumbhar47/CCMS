package com.iitbh.ccms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    private UserDetailsService userDetailsService;
    private final EmailService emailService;

    @Autowired
    public UsersService(EmailService emailService) {
        this.emailService = emailService;
    }

    public List<UserDetailsDB> getAllUserDetail(int page, int size) {
        // Fetch user details for the specified page and size
        Page<UserDetailsDB> userDetailsPage = usersRepository.findAll(PageRequest.of(page - 1, size));
        return userDetailsPage.getContent();
    }

    public int getTotalElements() {
        return (int) usersRepository.count();
    }

    public int getTotalPages(int size) {
        long totalElements = getTotalElements();
        return (int) Math.ceil((double) totalElements / size);
    }

    public boolean updateUserDetails(String userId, UserInfo userInfo) {
        // Find the user by userId
        Optional<UserDetailsDB> optionalUser = usersRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            UserDetailsDB existingUser = optionalUser.get();
            // Update the fields of existing user with the updated values
            existingUser.setUserName(userInfo.getUserName());
            existingUser.setRoles(
                    userInfo.getRoles()
                            .stream()
                            .map(role -> role.toString())
                            .collect(Collectors.toCollection(ArrayList::new))

            );
            existingUser.setName(userInfo.getName());
            existingUser.setEmail(userInfo.getEmail());
            existingUser.setStatus(userInfo.getStatus().toString());
            existingUser.setDepartment(userInfo.getDepartment());
            existingUser.setPhoneNumber(userInfo.getPhoneNumber());
            existingUser.setDateRegistered(userInfo.getDateRegistered());
            // Save the updated user details
            usersRepository.save(existingUser);
            System.out.println("User details updated successfully.");
            return true;
        } else {
            System.out.println("User with userId " + userId + " not found.");
            return false;
        }
    }

    public boolean deleteUserById(String userId) {
        try {
            usersRepository.delete(usersRepository.findByUserId(userId).get());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void createUser(UserDetailsDB newUserDetails) {
        usersRepository.save(newUserDetails);
        mailUserCredentials(newUserDetails);
    }

    private void mailUserCredentials(UserDetailsDB details) {
        emailService.sendSimpleMessage(
                details.getEmail(),
                "Welcome to CCMS",
                "A new account has been created for you on Campus Complaint Management System (CCMS).\nUsername: "
                        + details.getUsername() + "\n Password: " + details.getPassword());
    }

    public Optional<UserDetailsDB> singleUser(String username) {
        return usersRepository.findUsersByUserName(username);
    }

    @Override
    public UserDetailsDB loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserDetailsDB> user = usersRepository.findUsersByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user.get();
    }

    public UserInfo getUserInfo(String username) {
        Optional<UserDetailsDB> userDetailsDB = usersRepository.findUsersByUserName(username);
        if (!userDetailsDB.isEmpty()) {
            return userDetailsDB.get().convertToUserInfo();
        } else {
            return null;
        }
    }
}
