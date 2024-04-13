package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.ComplainOverviewApiDelegate;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplainOverviewDelegateImpl implements ComplainOverviewApiDelegate {
    private final ComplainService complainService;

    @Autowired
    public ComplainOverviewDelegateImpl(ComplainService complainService) {
        this.complainService  = complainService;
    }

    @Override
    public ResponseEntity<ComplaintPage> getComplaintsOverview(Integer pageNumber, Integer pageSize) {
        return ResponseEntity.ok(complainService.getAllComplains(pageNumber,pageSize));
    }
}