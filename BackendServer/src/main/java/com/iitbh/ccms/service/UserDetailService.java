package com.iitbh.ccms.service;

import com.iitbh.ccms.model_DB.UserDetailsDB;
import com.iitbh.ccms.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public List<UserDetailsDB> getAllUserDetail() {
        return userDetailsRepository.findAll();
    }
}
