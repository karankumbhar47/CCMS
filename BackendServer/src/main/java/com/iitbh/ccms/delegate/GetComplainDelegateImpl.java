package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.GetComplaintInfoApiDelegate;
import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetComplainDelegateImpl implements GetComplaintInfoApiDelegate {
    private final ComplaintService complaintService;

    public GetComplainDelegateImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }


    @Override
    public ResponseEntity<ComplaintDetails> getComplaintInfo(String id) {
        return ResponseEntity.ok(complaintService.getSingleComplaint(id));
    }
}
