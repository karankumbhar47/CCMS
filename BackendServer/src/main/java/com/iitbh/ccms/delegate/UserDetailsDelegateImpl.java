package com.iitbh.ccms.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.UserDetailsApiDelegate;
import com.iitbh.ccms.model.Pagination;
import com.iitbh.ccms.model.Role;
import com.iitbh.ccms.model.UserDetails;
import com.iitbh.ccms.model.UserDetails.StatusEnum;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.service.UsersService;

@Service
public class UserDetailsDelegateImpl implements UserDetailsApiDelegate {

    private final UsersService userDetailService;

    @Autowired
    public UserDetailsDelegateImpl(UsersService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<Pagination> getUsers(Integer page, Integer size) {
        List<UserDetailsDB> userDetailsList = userDetailService.getAllUserDetail(page, size);

        Pagination pagination = new Pagination();
        pagination.setTotalPages(userDetailService.getTotalPages(size));
        pagination.setTotalElements(userDetailService.getTotalElements());
        pagination.setPage(page);
        pagination.setSize(size);

        List<UserDetails> usersDetails = new ArrayList<>();
        for (UserDetailsDB userDetailsDB : userDetailsList) {
            UserDetails userDetails = new UserDetails();
            // usersDetail.setUserId(userDetailsDB.getUserId());
            userDetails.setUserId("1"); // TODO: Fix this
            userDetails.setUserName(userDetailsDB.getUserName());
            userDetails.setEmail(userDetailsDB.getEmail());
            // TODO: Update this
            try {
                userDetails.setRoles(
                        userDetailsDB.getRoles()
                                .stream()
                                .map(role -> Role.fromValue(role))
                                .collect(Collectors.toCollection(ArrayList::new)));

            } catch(IllegalArgumentException e) {
                System.err.println("Curropted user data for user + " + userDetailsDB.getUsername() + "; Invalid value for role");
            }
            try {
                userDetails.setStatus(StatusEnum.fromValue(userDetailsDB.getStatus()));
            } catch (IllegalArgumentException e) {
                userDetails.setStatus(StatusEnum.ACTIVE);
            }
            userDetails.setDateRegistered(userDetailsDB.getDateRegistered());
            usersDetails.add(userDetails);
        }
        pagination.setUsers(usersDetails);

        return ResponseEntity.ok(pagination);
    }
}
