package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UserDetailsUpdateApiDelegate;
import com.iitbh.ccms.model.UserDetails;
import com.iitbh.ccms.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsUpdateDelegateImpl implements UserDetailsUpdateApiDelegate {

    private final UsersService userDetailsService;

    @Autowired
    public UserDetailsUpdateDelegateImpl(UsersService userDetailUpdateService) {
        this.userDetailsService = userDetailUpdateService;
    }

    @Override
    public ResponseEntity<Void> userDetailUpdate(UserDetails userDetails) {
        userDetailsService.updateUserDetails(userDetails.getUserId().toString(), userDetails);
        return ResponseEntity.ok().build();
    }
}
