package com.iitbh.ccms.service;

import com.iitbh.ccms.utils.ComplaintUtils;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.model_db.Complaints;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.repository.UsersRepository;
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
    private final MongoTemplate mongoTemplate;
    private final LocationRepository locationRepository;
    private final ComplaintUtils complaintUtils;

    @Autowired
    public ComplainService(MongoTemplate mongoTemplate, ComplainRepository complaintRepository, UsersRepository usersRepository, LocationRepository locationRepository, ComplaintUtils complaintUtils) {
        this.complaintRepository = complaintRepository;
        this.mongoTemplate = mongoTemplate;
        this.usersRepository = usersRepository;
        this.locationRepository = locationRepository;
        this.complaintUtils = complaintUtils;
    }


    public List<ComplainOverview> getAllComplains(){
        List<ComplainOverview> returnList = new ArrayList<>();
        List<Complaints> list = complaintRepository.findAll();
        for(Complaints complains: list){
            ComplainOverview complainOverview = complains.convertToComplainOverview();
            UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
            if(userDetailsDB != null){
                complainOverview.setUserInfo(userDetailsDB.convertToUserDetails());
                System.out.println(complains.getComplainerId()+"got");
            }
            else{
                System.out.println(complains.getComplainerId()+"not got");
            }
            returnList.add(complainOverview);
        }
        Collections.reverse(returnList);
        return returnList;
    }

    public String SubmitComplain(ComplainSubmit complainSubmit){
        List<Complaints> list =  complaintRepository.findAll();
        while(true) {
            String complainId = complaintUtils.getUniqueComplaintId();
            for (Complaints complains : list) {
                if(!complainId.equals(complains.getComplaintId())){
                    Complaints complainsWithId = new Complaints();
                    complainsWithId.convertToComplaints(complainSubmit, complainId);
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

    public ComplainOverview getSingleComplaint(String ComplaintId){
        Complaints complains = complaintRepository.findByComplaintId(ComplaintId);
        UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
        ComplainOverview complainOverview = complains.convertToComplainOverview();
        if(userDetailsDB != null){
            complainOverview.setUserInfo(userDetailsDB.convertToUserDetails());
            System.out.println(complains.getComplainerId()+"got");
        }
        else{
            System.out.println(complains.getComplainerId()+"not got");
        }
        return complainOverview;
    }

    public List<ComplainOverview> getFilteredComplain(List<String> tags, String totime, String fromtime) {
        List<ComplainOverview> allComplaints = getAllComplains();
        List<ComplainOverview> tagFilteredComplaints = filterComplaintByTag(allComplaints, tags);
        List<ComplainOverview> filteredComplaints = new ArrayList<>();

        for (ComplainOverview complaint: tagFilteredComplaints){
            if (dateIsInBetween(complaint.getRegistrationDate(), totime, fromtime)){
                filteredComplaints.add(complaint);
            }
        }
        return filteredComplaints;
    }

    public List<ComplainOverview> filterComplaintByTag(List<ComplainOverview> complaints, List<String> tags){
        List<ComplainOverview> filtered = new ArrayList<>();
        if (tags.isEmpty()){
            return complaints;
        }
        for (ComplainOverview complaint: complaints){
            if (tags.stream().anyMatch(tag->tag.equals(complaint.getBuildingName()))){
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

