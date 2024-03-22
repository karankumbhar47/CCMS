package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.AuthApiDelegate;
import com.iitbh.ccms.model.LoginUser200Response;
import com.iitbh.ccms.model.LoginUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginDelegateImpl implements AuthApiDelegate {

    @Override
    public ResponseEntity<LoginUser200Response> loginUser(LoginUserRequest loginUserRequest) {
        LoginUser200Response response = new LoginUser200Response();
        String userName = loginUserRequest.getUsername();
        String password = loginUserRequest.getPassword();
        // do some processing on token;
        response.setJwtToken("jwtToken");
        return ResponseEntity.ok(response);
    }
}
