package com.iitbh.ccms.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.iitbh.ccms.model_db.JwtResponse;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.security.JWTHelper;
import com.iitbh.ccms.service.UsersService;

@Component
public class AuthController {

    @Autowired
    private UsersService userService;

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JWTHelper helper;

    // private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public ResponseEntity<String> login(String username, String password, String role) {

        Optional<UserDetailsDB> optionalUser = userService.singleUser(username);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }
        assert !optionalUser.isEmpty();
        UserDetailsDB user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            System.out.println("Password not matched");
            return new ResponseEntity<>("Password not matched", HttpStatus.UNAUTHORIZED);
        }

        List<String> roles = user.getRoles();
        if(roles.isEmpty()){
            System.err.println("[ERROR::Authentication] Curropted user record for user " + user.getUserName() + "; No Role found");
            return new ResponseEntity<>("No Valid role", HttpStatus.UNAUTHORIZED);
        }
        if(!roles.contains(role)){
            return new ResponseEntity<>("You are at wrong portal", HttpStatus.UNAUTHORIZED);
        }

        String token = this.helper.generateToken(user, user.getName());

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(user.getUsername())
                .build();

        return new ResponseEntity<>(response.getJwtToken(), HttpStatus.OK);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
