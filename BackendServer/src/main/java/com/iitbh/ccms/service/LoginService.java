package com.iitbh.ccms.service;

import com.iitbh.ccms.model.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginService {
    public final  HashMap<LoginRequest,String> map = new HashMap<>();

    public String loginUser(LoginRequest loginRequest){
        map.put(loginRequest,"user-id");
        return "user-id";
    }
}
