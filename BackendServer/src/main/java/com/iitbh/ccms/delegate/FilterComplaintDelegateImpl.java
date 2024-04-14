package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.FilterComplaintNewApi;
import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterComplaintDelegateImpl implements FilterComplaintNewApi {

    @Autowired
    private ComplaintService complaintService;
    @Override
    public ResponseEntity<List<ComplaintDetails>> filterComplaintNew(List<String> tags, String totime, String fromtime) {
        return ResponseEntity.ok(complaintService.getFilteredComplain(tags, totime, fromtime));
    }
}
