package com.iitbh.ccms.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.StatsApiDelegate;
import com.iitbh.ccms.model.Stats;
import com.iitbh.ccms.model.ComplaintInfo.StatusEnum;
import com.iitbh.ccms.service.ComplaintService;
import com.iitbh.ccms.service.UsersService;

@Service
class StatsDelegateImpl implements StatsApiDelegate{
    private final UsersService usersService;
    private final ComplaintService complaintService;


    @Autowired
    public StatsDelegateImpl(UsersService usersService, ComplaintService complaintService) {
        this.usersService = usersService;
        this.complaintService = complaintService;
    }

    @Override
    public ResponseEntity<Stats> stats() {
        Stats s = new Stats();
        s.setUsers(usersService.getTotalElements());
        s.setComplaints(complaintService.getCount());
        s.setPendingComplaints(complaintService.getCountByStatus(StatusEnum.OPEN));
        return ResponseEntity.ok(s);
    }
}
