package com.iitbh.ccms.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.LoginApiDelegate;
import com.iitbh.ccms.config.AuthController;
import com.iitbh.ccms.model.LoginRequest;
import com.iitbh.ccms.service.LoginService;

@Service
public class LoginDelegateImpl implements LoginApiDelegate {
    private final LoginService loginService;

    @Autowired
    private AuthController authController;

    public LoginDelegateImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        return authController.login(loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getRole().toString());
    }
}
