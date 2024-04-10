package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.DeleteUserApiDelegate;
import com.iitbh.ccms.model.DeleteUserRequest;
import com.iitbh.ccms.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserDelegateImpl implements DeleteUserApiDelegate {

    private final UsersService deleteUserService;

    @Autowired
    public DeleteUserDelegateImpl(UsersService deleteUserService) {
        this.deleteUserService = deleteUserService;
    }

    @Override
    public ResponseEntity<Void> deleteUser(DeleteUserRequest deleteUserRequest) {
        // Extract userId from the request if necessary
        int userId = deleteUserRequest.getUserId();

        // Call the UserService to delete the user
        deleteUserService.deleteUserById(userId);

        boolean deleted = true;

        if (deleted) {
            return ResponseEntity.ok().build(); // User deleted successfully
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }
    }
}
