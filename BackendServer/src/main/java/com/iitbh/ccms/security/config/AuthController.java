package com.iitbh.ccms.security.config;

import com.iitbh.ccms.model.LoginRequest;
import com.iitbh.ccms.security.models.JwtRequest;
import com.iitbh.ccms.security.models.JwtResponse;
import com.iitbh.ccms.security.models.Myuser;
import com.iitbh.ccms.security.security.JWTHelper;
import com.iitbh.ccms.security.services.MyuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
@Component
public class AuthController {

    @Autowired
    private MyuserService myuserService;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JWTHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        System.out.println("Authcontroller called");

        try {
            this.doAuthenticate(request.getUsername(), request.getPassword());
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Myuser> user = myuserService.singleEmail(request.getUsername());

        UserDetails userDetails = User.builder().username(request.getUsername())
                .password(passwordEncoder().encode(request.getPassword()))
                .roles(user.get().getRole()).build();
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails, user.get().getFirstname()+" "+user.get().getLastname());

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();
        return new ResponseEntity<>(response.getJwtToken(), HttpStatus.OK);

    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void doAuthenticate(String email, String password) {
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//
//        try{
//            manager.authenticate(authentication);
//        } catch (BadCredentialsException e) {
//            throw new RuntimeException("Invalid Username or Password !!");
//        }
//        if (!password.equals("pass")) {
//            throw new RuntimeException("Invalid Username or Password !!");
//        }
//        System.out.println(myuserService.singleEmail(email));

        Optional<Myuser> user = myuserService.singleEmail(email);

        if (user.isEmpty()) {
            throw new RuntimeException("Email does not exist!!");
        } else if (!user.get().getPassword().equals(password)) {
            throw new RuntimeException("Wrong password!!");

        }
    }


}
