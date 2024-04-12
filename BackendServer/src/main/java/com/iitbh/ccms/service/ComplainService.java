package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.model_db.Complaints;
import com.iitbh.ccms.model_db.LocationDB;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.repository.UsersRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.iitbh.ccms.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ComplainService {
    private final ComplainRepository complaintRepository;
    private final UsersRepository usersRepository;
    private final MongoTemplate mongoTemplate;
    private final LocationRepository locationRepository;

    @Autowired
    public ComplainService(MongoTemplate mongoTemplate, ComplainRepository complaintRepository, UsersRepository usersRepository, LocationRepository locationRepository) {
        this.complaintRepository = complaintRepository;
        this.mongoTemplate = mongoTemplate;
        this.usersRepository = usersRepository;
        this.locationRepository = locationRepository;
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
            String complainId = getUniqueComplaintId();
            for (Complaints complains : list) {
                if(!complainId.equals(complains.getComplaintId())){
                    Complaints complainsWithId = new Complaints();
                    complainsWithId.convertToComplaints(complainSubmit, complainId);
                    complainsWithId.setMailIds(getEmailsByZoneAndCategory(complainsWithId.getZone(),complainsWithId.getComplaintCriteria()));
                    System.out.println(complainsWithId.getMailIds());
                    complaintRepository.save(complainsWithId);
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

    public List<String> getEmailsByZoneAndCategory(String zoneName, String categoryName) {
        Optional<LocationDB> response = locationRepository.findByZoneName(zoneName);
        if(response.isPresent()){
            System.out.println(response.get().toString());
            for(LocationDB.Category category: response.get().getCategories()){
                if(category.getName().equals(categoryName)){
                    return category.getEmails();
                }
            }
        }
        return new ArrayList<>();
//        System.out.println(zoneName+" "+categoryName);
//        Query query = new Query(Criteria.where("zoneName").is(zoneName)
//                .and("categories.name").is(categoryName));
//        query.fields().include("categories.emails");
//        List<LocationDB> locations = mongoTemplate.find(query, LocationDB.class);
//        if(locations.isEmpty() || locations.get(0)==null){
//            return  new ArrayList<>();
//        }
//        System.out.println(locations.size());
//        System.out.println(locations.get(0).toString());
//        return  new ArrayList<>();
//        return extractEmailsFromLocations(locations,categoryName);
    }

//    private List<String> extractEmailsFromLocations(List<LocationDB> locations,String categoryName) {
//        List<String> emails = new ArrayList<>();
//        for (LocationDB location : locations) {
//            for (LocationDB.Category category : location.getCategories()) {
//                if (category.getName().equals(categoryName)) {
//                    emails.addAll(category.getEmails());
//                }
//            }
//        }
//        return emails;
//    }
//
}

