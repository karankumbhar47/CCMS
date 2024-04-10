package com.iitbh.ccms.service;

import com.iitbh.ccms.Utils;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.model_db.Complaints;
import com.iitbh.ccms.repository.ComplainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ComplainService {
    private static final Logger log = LoggerFactory.getLogger(ComplainService.class);
    private final ComplainRepository complaintRepository;

    @Autowired
    public ComplainService(ComplainRepository complainRepository) {
        this.complaintRepository = complainRepository;
    }

    public List<ComplainOverview> getAllComplains(){
        List<ComplainOverview> returnList = new ArrayList<>();
        List<Complaints> list = complaintRepository.findAll();
        for(Complaints complains: list){
            System.out.println(complains.getComplaintId());
            System.out.println(complains.getComplaintCriteria());
            ComplainOverview complainOverview = complains.convertToComplainOverview();
            returnList.add(complainOverview);
        }
        Collections.reverse(returnList);
        return returnList;
    }

    public String SubmitComplain(ComplainSubmit complainSubmit){
        List<Complaints> list =  complaintRepository.findAll();
        while(true) {
            String complainId = getUniqueComplaintId();
            for (Complaints complains : list) {
                if(!complainId.equals(complains.getComplaintId())){
                    Complaints complainsWithId = new Complaints();
                    complainsWithId.convertToComplaints(complainSubmit, complainId);
                    complaintRepository.save(complainsWithId);
                    return complainId;
                }
            }
        }
    }

    public ComplainOverview getSingleComplaint(String ComplaintId){
        Complaints complains = complaintRepository.findByComplaintId(ComplaintId);
        return complains.convertToComplainOverview();
    }

    public String getUniqueComplaintId(){
        List<Complaints> list =  complaintRepository.findAll();
        List<String> complainIds = list.stream()
                .map(Complaints::getComplaintId)
                .toList();
        while (true) {
            String complainId = Utils.generateId(40);
            if (!complainIds.contains(complainId)) {
                return complainId;
            }
        }
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

