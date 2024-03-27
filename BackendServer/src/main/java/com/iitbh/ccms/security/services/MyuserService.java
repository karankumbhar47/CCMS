package com.iitbh.ccms.security.services;

import com.iitbh.ccms.security.models.Myuser;
import com.iitbh.ccms.security.repository.MyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyuserService {

    @Autowired
    private MyuserRepository myuserRepository;

    public Optional<Myuser> singleEmail(String email) {
        return myuserRepository.findMyuserByEmail(email);
    }
}
