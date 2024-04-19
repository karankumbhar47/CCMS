package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.FilterComplaintApi;
import com.iitbh.ccms.api.FilterComplaintApiDelegate;
import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.service.ComplaintService;
import com.iitbh.ccms.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterComplaintDelegateImpl implements FilterComplaintApiDelegate {


    private final FilterService filterService;
    @Autowired
    public FilterComplaintDelegateImpl(FilterService filterService) {
        this.filterService = filterService;
    }

    @Override
    public ResponseEntity<ComplaintPage> filterComplaint(Integer pageNumber, Integer pageSize, List<String> tags, String totime, String fromtime, String status) {
        return ResponseEntity.ok(filterService.getFilteredComplaint(tags, totime, fromtime, status, pageNumber, pageSize));
    }
}
