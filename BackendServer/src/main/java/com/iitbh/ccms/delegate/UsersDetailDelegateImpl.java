package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UsersDetailApiDelegate;
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
    private final UserDetailService userDetailService ;

    @Autowired
    public UsersDetailDelegateImpl(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public ResponseEntity<List<UsersDetail>> usersDetail() {
        List<UserDetailsDB> list = userDetailService.getAllUserDetail();
        System.out.println(list.size());
        List<UsersDetail>  returnList =  new ArrayList<UsersDetail>();
        for(UserDetailsDB userDetailsDB: list){
            UsersDetail usersDetail = new UsersDetail();
            usersDetail.setUserId(userDetailsDB.getUserId());
            usersDetail.setUserName(userDetailsDB.getUserName());
            usersDetail.setRole(userDetailsDB.getRole());
            usersDetail.setStatus(userDetailsDB.getStatus());
            usersDetail.dateRegistered(userDetailsDB.getDateRegistered());
            returnList.add(usersDetail);
        }
        return ResponseEntity.ok(returnList);
    }
}
