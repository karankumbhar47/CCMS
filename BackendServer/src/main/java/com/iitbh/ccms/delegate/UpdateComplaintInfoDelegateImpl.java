package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UpdateComplaintInfoApiDelegate;
import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateComplaintInfoDelegateImpl implements UpdateComplaintInfoApiDelegate {
    private final ComplaintService complaintService;

    @Autowired
    public UpdateComplaintInfoDelegateImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Override
    public ResponseEntity<Void> updateComplaintInfo(String id, ComplaintInfo complaintInfo) {
        complaintService.updateComplaintInfo(id,complaintInfo);
        return null;
    }
}
