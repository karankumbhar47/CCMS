package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.SubmitComplainApiDelegate;
import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubmitComplainDelegateImpl implements SubmitComplainApiDelegate {
    private final ComplaintService complaintService;

    public SubmitComplainDelegateImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Override
    public ResponseEntity<String> submitComplaint(ComplaintInfo complaintInfo) {
        return ResponseEntity.ok(complaintService.SubmitComplain(complaintInfo));
    }
}
