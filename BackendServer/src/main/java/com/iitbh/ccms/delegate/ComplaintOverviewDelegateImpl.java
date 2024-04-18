package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.ComplaintOverviewApiDelegate;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComplaintOverviewDelegateImpl implements ComplaintOverviewApiDelegate {
    private final ComplaintService complaintService;

    @Autowired
    public ComplaintOverviewDelegateImpl(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Override
    public ResponseEntity<ComplaintPage> getComplaintsOverview(Integer pageNumber, Integer pageSize, String userId) {
        return ResponseEntity.ok(complaintService.getAllComplains(pageNumber,pageSize,userId));
    }
}