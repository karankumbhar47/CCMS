package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.model_db.Complaints;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.iitbh.ccms.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ComplainService {
    private final ComplainRepository complaintRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ComplainService(MongoTemplate mongoTemplate, ComplainRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
        this.mongoTemplate = mongoTemplate;
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

    public String getUniqueComplaintId() {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Complaints> complaintsForToday = findByDate(currentDate);
        int complaintNumber = complaintsForToday.size() + 1;
        String formattedComplaintNumber = String.format("%03d", complaintNumber);
        return formattedDate + "_" + formattedComplaintNumber;
    }

    public List<Complaints> findByDate(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String regexPattern = "^" + formattedDate.replace("-", "\\-") + ".*"; // Adjust the regex pattern
        Query query = Query.query(Criteria.where("registrationDate").regex(regexPattern));
        return mongoTemplate.find(query, Complaints.class);
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

