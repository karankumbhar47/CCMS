package com.iitbh.ccms.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.model_db.Complaint;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.ComplaintRepository;
import com.iitbh.ccms.repository.ComplaintRepository;
import com.iitbh.ccms.repository.UsersRepository;
import com.iitbh.ccms.utils.ComplaintUtils;

@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UsersRepository usersRepository;
    private final ComplaintUtils complaintUtils;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, UsersRepository usersRepository,
            ComplaintUtils complaintUtils) {
        this.complaintRepository = complaintRepository;
        this.usersRepository = usersRepository;
        this.complaintUtils = complaintUtils;
    }

    public ComplaintPage getAllComplains(int page, int size, String userId) {
        ComplaintPage complaintPage = new ComplaintPage();
        complaintPage.setTotalPages(complaintUtils.getTotalPages(size, userId));
        complaintPage.setComplaintList(complaintUtils.getComplaintPage(page, size, userId));
        return complaintPage;
    }

    public String SubmitComplain(ComplaintInfo complaintInfo) {
        String complainId = complaintUtils.getUniqueComplaintId();
        Complaint complainsWithId = new Complaint();
        complainsWithId.convertToComplaints(complaintInfo, complainId);
        complainsWithId.setMailIds(complaintUtils.getEmailsByZoneAndCategory(complainsWithId.getZone(),
                complainsWithId.getComplaintCriteria()));
        complaintRepository.save(complainsWithId);
        if (!complainsWithId.getMailIds().isEmpty()) {
            complaintUtils.sendComplaintMail(complainsWithId.getMailIds(), complainsWithId);
        }
        return complainId;
    }

    public ComplaintDetails getSingleComplaint(String ComplaintId) {
        Complaint complains = complaintRepository.findByComplaintId(ComplaintId);
        Optional<UserDetailsDB> userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
        ComplaintDetails complainOverview = complains.convertToComplainOverview();
        if (!userDetailsDB.isEmpty()) {
            complainOverview.setUserInfo(userDetailsDB.get().convertToUserInfo());
        }
        return complainOverview;
    }

    public void updateComplaintInfo(String complainId, ComplaintInfo complaintInfo) {
        Complaint previousComplaint = complaintRepository.findByComplaintId(complainId);
        previousComplaint.convertToComplaints(complaintInfo, complainId);
        complaintRepository.save(previousComplaint);
    }

}
