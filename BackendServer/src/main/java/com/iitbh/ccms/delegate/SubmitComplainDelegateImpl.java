package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.SubmitComplainApiDelegate;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.service.ComplainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubmitComplainDelegateImpl implements SubmitComplainApiDelegate {
    private final ComplainService complainService;

    public SubmitComplainDelegateImpl(ComplainService complainService) {
        this.complainService = complainService;
    }

    @Override
    public ResponseEntity<String> submitComplaint(ComplainSubmit complainSubmit) {
        return ResponseEntity.ok(complainService.SubmitComplain(complainSubmit));
    }
}
