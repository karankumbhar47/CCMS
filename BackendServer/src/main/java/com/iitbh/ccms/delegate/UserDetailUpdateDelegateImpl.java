package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UserDetailUpdateApiDelegate;
import com.iitbh.ccms.model.UserDetailUpdate;
import com.iitbh.ccms.service.UserDetailUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserDetailUpdateDelegateImpl implements UserDetailUpdateApiDelegate {

    private final UserDetailUpdateService userDetailUpdateService;

    @Autowired
    public UserDetailUpdateDelegateImpl(UserDetailUpdateService userDetailUpdateService) {
        this.userDetailUpdateService = userDetailUpdateService;
    }


    @Override
    public ResponseEntity<Void> userDetailUpdate(UserDetailUpdate userDetailUpdate) {
        userDetailUpdateService.updateUserDetails(userDetailUpdate.getUserId(),userDetailUpdate);
        return ResponseEntity.ok().build();
    }
}
