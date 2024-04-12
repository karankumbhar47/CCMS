package com.iitbh.ccms.delegate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.UserDetailsApiDelegate;
import com.iitbh.ccms.model.Pagination;
import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.service.UsersService;

@Service
public class UserDetailsDelegateImpl implements UserDetailsApiDelegate {

    private final UsersService userDetailService;

    @Autowired
    public UserDetailsDelegateImpl(UsersService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<Pagination> getUsers(Integer page, Integer size) {
        List<UserDetailsDB> userDetailsList = userDetailService.getAllUserDetail(page, size);

        Pagination pagination = new Pagination();
        pagination.setTotalPages(userDetailService.getTotalPages(size));
        pagination.setTotalElements(userDetailService.getTotalElements());
        pagination.setPage(page);
        pagination.setSize(size);

        List<UserInfo> usersDetails = userDetailsList.stream()
                .map(UserDetailsDB::convertToUserInfo)
                .collect(Collectors.toList());

        pagination.setUsers(usersDetails);

        return ResponseEntity.ok(pagination);
    }
}
