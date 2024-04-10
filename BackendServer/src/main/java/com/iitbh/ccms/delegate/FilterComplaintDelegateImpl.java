package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.FilterComplaintNewApi;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterComplaintDelegateImpl implements FilterComplaintNewApi {

    @Autowired
    private ComplainService complainService;
    @Override
    public ResponseEntity<List<ComplainOverview>> filterComplaintNew(List<String> tags, String totime, String fromtime) {
        return ResponseEntity.ok(complainService.getFilteredComplain(tags, totime, fromtime));
    }
}
