package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetComplaintInfoApiDelegate;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetComplainDelegateImpl implements GetComplaintInfoApiDelegate {
    private final ComplainService complainService;

    public GetComplainDelegateImpl(ComplainService complainService) {
        this.complainService  = complainService;
    }


    @Override
    public ResponseEntity<ComplainOverview> getComplaintInfo(String id) {
        return ResponseEntity.ok(complainService.getSingleComplaint(id));
    }
}
