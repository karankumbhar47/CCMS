package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UserDetailUpdateApiDelegate;
import com.iitbh.ccms.model.UserDetailUpdateRequest;
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
    public ResponseEntity<Void> userDetailUpdate(UserDetailUpdateRequest userDetailUpdateRequest) {
        userDetailUpdateService.updateUserDetails(userDetailUpdateRequest.getUserId(),userDetailUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
