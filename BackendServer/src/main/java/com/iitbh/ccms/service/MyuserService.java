package com.iitbh.ccms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model_db.Myuser;
import com.iitbh.ccms.repository.MyuserRepository;

@Service
public class MyuserService {

    @Autowired
    private MyuserRepository myuserRepository;

    public Optional<Myuser> singleEmail(String email) {
        return myuserRepository.findMyuserByEmail(email);
    }
}
