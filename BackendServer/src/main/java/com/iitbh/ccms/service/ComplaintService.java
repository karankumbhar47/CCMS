package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.utils.ComplaintUtils;
import com.iitbh.ccms.model_db.Complaint;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.UsersRepository;
import com.iitbh.ccms.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UsersRepository usersRepository;
    private final ComplaintUtils complaintUtils;

    @Autowired
    public ComplaintService(ComplaintRepository complaintRepository, UsersRepository usersRepository, ComplaintUtils complaintUtils) {
        this.complaintRepository = complaintRepository;
        this.usersRepository = usersRepository;
        this.complaintUtils = complaintUtils;
    }


    public ComplaintPage getAllComplains(int page, int size, String userId){
        ComplaintPage complaintPage = new ComplaintPage();
        complaintPage.setTotalPages(complaintUtils.getTotalPages(size,userId));
        complaintPage.setComplaintList(complaintUtils.getComplaintPage(page,size, userId));
        return complaintPage;
    }

    public String SubmitComplain(ComplaintInfo complaintInfo) {
        String complainId = complaintUtils.getUniqueComplaintId();
        Complaint complainsWithId = new Complaint();
        complainsWithId.convertToComplaints(complaintInfo, complainId);
        complainsWithId.setMailIds(complaintUtils.getEmailsByZoneAndCategory(complainsWithId.getZone(), complainsWithId.getComplaintCriteria()));
        complaintRepository.save(complainsWithId);
        if (!complainsWithId.getMailIds().isEmpty()) {
            complaintUtils.sendComplaintMail(complainsWithId.getMailIds(), complainsWithId);
        }
        return complainId;
    }

    public ComplaintDetails getSingleComplaint(String ComplaintId){
        Complaint complains = complaintRepository.findByComplaintId(ComplaintId);
        UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
        ComplaintDetails complainOverview = complains.convertToComplainOverview();
        if(userDetailsDB != null){
            complainOverview.setUserInfo(userDetailsDB.convertToUserDetails());
        }
        return complainOverview;
    }

    public List<ComplaintDetails> getFilteredComplain(List<String> tags, String totime, String fromtime) {
        List<ComplaintDetails> allComplaints = complaintUtils.getComplaintPage(0,10,null);
        List<ComplaintDetails> tagFilteredComplaints = filterComplaintByTag(allComplaints, tags);
        List<ComplaintDetails> filteredComplaints = new ArrayList<>();

        for (ComplaintDetails complaint: tagFilteredComplaints){
            if (dateIsInBetween(complaint.getComplaintInfo().getRegistrationDate(), totime, fromtime)){
                filteredComplaints.add(complaint);
            }
        }
        return filteredComplaints;
    }

    public List<ComplaintDetails> filterComplaintByTag(List<ComplaintDetails> complaints, List<String> tags){
        List<ComplaintDetails> filtered = new ArrayList<>();
        if (tags.isEmpty()){
            return complaints;
        }
        for (ComplaintDetails complaint: complaints){
            if (tags.stream().anyMatch(tag->tag.equals(complaint.getComplaintInfo().getBuildingName()))){
                filtered.add(complaint);
            }
        }
        return filtered;
    }

    public boolean dateIsInBetween(String date, String todate, String fromdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (todate.isEmpty()){ todate = "9999-01-01";}
        if (fromdate.isEmpty()){ fromdate = "0000-01-01";}
        LocalDate regDate = LocalDate.parse(date.substring(0,10), formatter);
        LocalDate toDate = LocalDate.parse(todate, formatter);
        LocalDate fromDate = LocalDate.parse(fromdate, formatter);
        return (regDate.isEqual(fromDate) || regDate.isAfter(fromDate)) && (regDate.isEqual(toDate) || regDate.isBefore(toDate));
    }

    public void updateComplaintInfo(String complainId,ComplaintInfo complaintInfo){
        Complaint previousComplaint = complaintRepository.findByComplaintId(complainId);
        previousComplaint.convertToComplaints(complaintInfo,complainId);
        complaintRepository.save(previousComplaint);
    }

}

