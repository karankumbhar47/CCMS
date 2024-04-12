package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.utils.ComplaintUtils;
import com.iitbh.ccms.model_db.Complaints;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.iitbh.ccms.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ComplainService {
    private final ComplainRepository complaintRepository;
    private final UsersRepository usersRepository;
    private final ComplaintUtils complaintUtils;

    @Autowired
    public ComplainService(ComplainRepository complaintRepository, UsersRepository usersRepository, ComplaintUtils complaintUtils) {
        this.complaintRepository = complaintRepository;
        this.usersRepository = usersRepository;
        this.complaintUtils = complaintUtils;
    }


    public List<ComplaintDetails> getAllComplains(int page,int size){
        Page<Complaints> complaintsPage = complaintRepository.findAll(PageRequest.of(page - 1, size));
        List<Complaints> list = complaintsPage.getContent();
        List<ComplaintDetails> returnList = new ArrayList<>();
        for(Complaints complains: list){
            ComplaintDetails complainOverview = complains.convertToComplainOverview();
            complainOverview.setTotalPages(complaintUtils.getTotalPages(size));
            complainOverview.setPageNumber(page);
            complainOverview.setPageSize(size);
            UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
            if(userDetailsDB != null){ complainOverview.setUserInfo(userDetailsDB.convertToUserDetails()); }
            returnList.add(complainOverview);
        }
        Collections.reverse(returnList);
        return returnList;
    }

    public String SubmitComplain(ComplaintInfo complaintInfo){
        List<Complaints> list =  complaintRepository.findAll();
        while(true) {
            String complainId = complaintUtils.getUniqueComplaintId();
            for (Complaints complains : list) {
                if(!complainId.equals(complains.getComplaintId())){
                    Complaints complainsWithId = new Complaints();
                    complainsWithId.convertToComplaints(complaintInfo, complainId);
                    complainsWithId.setMailIds(complaintUtils.getEmailsByZoneAndCategory(complainsWithId.getZone(),complainsWithId.getComplaintCriteria()));
                    complaintRepository.save(complainsWithId);
                    if(!complainsWithId.getMailIds().isEmpty()) {
                        complaintUtils.sendComplaintMail(complainsWithId.getMailIds(), complainsWithId);
                    }
                    return complainId;
                }
            }
        }
    }

    public ComplaintDetails getSingleComplaint(String ComplaintId){
        Complaints complains = complaintRepository.findByComplaintId(ComplaintId);
        UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
        ComplaintDetails complainOverview = complains.convertToComplainOverview();
        if(userDetailsDB != null){
            complainOverview.setUserInfo(userDetailsDB.convertToUserDetails());
        }
        return complainOverview;
    }

    public List<ComplaintDetails> getFilteredComplain(List<String> tags, String totime, String fromtime) {
        List<ComplaintDetails> allComplaints = getAllComplains(0,10);
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

}

