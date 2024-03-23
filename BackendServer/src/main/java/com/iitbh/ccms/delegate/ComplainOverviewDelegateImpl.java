package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.ComplainOverviewApiDelegate;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplainOverviewDelegateImpl implements ComplainOverviewApiDelegate {
    private final ComplainService complainService;
    public ComplainOverviewDelegateImpl(ComplainService complainService) {
        this.complainService  = complainService;
    }

    @Override
    public ResponseEntity<List<ComplainOverview>> getComplaintsOverview() {
        return ResponseEntity.ok(complainService.getAllComplains());
    }

}