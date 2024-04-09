package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UsersDetailApiDelegate;
import com.iitbh.ccms.model.Pagination;
import com.iitbh.ccms.model.UsersDetail;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsersDetailDelegateImpl implements UsersDetailApiDelegate {

    private final UserDetailService userDetailService;

    @Autowired
    public UsersDetailDelegateImpl(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<Pagination> getUsers(Integer page , Integer size) {

//        System.out.println(getUsersRequest);
//        if (getUsersRequest != null) {
//            page = getUsersRequest.getPage() != null ? getUsersRequest.getPage() : page;
//            size = getUsersRequest.getSize() != null ? getUsersRequest.getSize() : size;
//            System.out.println(page);
//        }else{
//            System.out.println("else");
//        }

        List<UserDetailsDB> userDetailsList = userDetailService.getAllUserDetail(page, size);

        Pagination pagination = new Pagination();
        pagination.setTotalPages(userDetailService.getTotalPages(size));
        pagination.setTotalElements(userDetailService.getTotalElements());
        pagination.setPage(page);
        pagination.setSize(size);

        List<UsersDetail> usersDetails = new ArrayList<>();
        for (UserDetailsDB userDetailsDB : userDetailsList) {
            UsersDetail usersDetail = new UsersDetail();
            usersDetail.setUserId(userDetailsDB.getUserId());
            usersDetail.setUserName(userDetailsDB.getUserName());
            usersDetail.setEmail(userDetailsDB.getEmail());
            usersDetail.setRole(userDetailsDB.getRole());
            usersDetail.setStatus(userDetailsDB.getStatus());
            usersDetail.setDateRegistered(userDetailsDB.getDateRegistered());
            usersDetails.add(usersDetail);
        }
        pagination.setUsers(usersDetails);

        return ResponseEntity.ok(pagination);
    }
}
