package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetUserInfoApiDelegate;
import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoDelegateImpl implements GetUserInfoApiDelegate {
    private final UsersService usersService;

    @Autowired
    public GetUserInfoDelegateImpl(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public ResponseEntity<UserInfo> getUserInfo(String username) {
        System.out.println(username);
        if (usersService.getUserInfo(username) != null) {
            return ResponseEntity.ok(usersService.getUserInfo(username));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
