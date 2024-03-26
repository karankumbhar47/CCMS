package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetComplaintApiDelegate;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.http.ResponseEntity;

public class GetComplainDelegateImpl implements GetComplaintApiDelegate {
    private final ComplainService complainService;

    public GetComplainDelegateImpl(ComplainService complainService) {
        this.complainService  = complainService;
    }

    @Override
    public ResponseEntity<ComplainOverview> getComplaint(String complaintId) {
        return ResponseEntity.ok(complainService.getSingleComplain(complaintId));
    }
}
