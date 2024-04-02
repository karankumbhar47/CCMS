package com.iitbh.ccms.delegate;


import com.iitbh.ccms.api.CreateUserApiDelegate;
import com.iitbh.ccms.model.UserDetailUpdate;
import com.iitbh.ccms.service.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateUserDelegateImpl implements CreateUserApiDelegate {
    private final CreateUserService createUserService;

    @Autowired
    public CreateUserDelegateImpl(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @Override
    public ResponseEntity<Void> createUser(UserDetailUpdate userDetailUpdate) {
        createUserService.createUser(userDetailUpdate);
        return ResponseEntity.ok().build();
    }
}
