package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.LoginApiDelegate;
import com.iitbh.ccms.model.LoginRequest;
import com.iitbh.ccms.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginDelegateImpl implements LoginApiDelegate {
    private final LoginService loginService;

    public LoginDelegateImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(loginService.loginUser(loginRequest));
    }
}
