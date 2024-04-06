package com.iitbh.ccms.service;

import com.iitbh.ccms.model_db.UserDetailUpdateDB;
import com.iitbh.ccms.repository.CreateUserRepository;
import com.iitbh.ccms.repository.DeleteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {

    private final CreateUserRepository deleteUserRepository;

    @Autowired
    public DeleteUserService(CreateUserRepository deleteUserRepository) {
        this.deleteUserRepository = deleteUserRepository;
    }

    public void deleteUserById(int userId) {
        deleteUserRepository.delete(deleteUserRepository.findByUserId(userId));
    }
}