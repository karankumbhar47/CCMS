package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetUserInfoApiDelegate;
import com.iitbh.ccms.model.UserDetails;
import com.iitbh.ccms.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoDelegateImpl implements GetUserInfoApiDelegate {
    private final UsersService usersService;

    @Autowired
    public GetUserInfoDelegateImpl( UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public ResponseEntity<UserDetails> getUserInfo(String userId) {
        System.out.println(userId);
        if(usersService.getUserInfo(userId)!=null) {
            return ResponseEntity.ok(usersService.getUserInfo(userId));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
