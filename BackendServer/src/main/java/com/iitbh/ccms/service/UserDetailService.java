package com.iitbh.ccms.service;

import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public List<UserDetailsDB> getAllUserDetail(int page, int size) {
        // Fetch user details for the specified page and size
        Page<UserDetailsDB> userDetailsPage = userDetailsRepository.findAll(PageRequest.of(page - 1, size));
        return userDetailsPage.getContent();
    }

    public int getTotalElements() {
        // Get total number of user details

        return (int) userDetailsRepository.count();
    }

    public int getTotalPages(int size) {
        // Calculate total number of pages based on page size and total elements
        long totalElements = getTotalElements();
        return (int) Math.ceil((double) totalElements / size);
    }
}