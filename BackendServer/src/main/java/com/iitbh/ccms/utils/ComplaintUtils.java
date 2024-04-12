package com.iitbh.ccms.utils;

import com.iitbh.ccms.model_db.Complaints;
import com.iitbh.ccms.model_db.LocationDB;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintUtils {

    private final MongoTemplate mongoTemplate;
    private final LocationRepository locationRepository;
    private final EmailService emailService;

    @Autowired
    public ComplaintUtils(MongoTemplate mongoTemplate, LocationRepository locationRepository, EmailService emailService) {
        this.mongoTemplate = mongoTemplate;
        this.locationRepository = locationRepository;
        this.emailService = emailService;
    }

    public String getUniqueComplaintId() {
        System.out.println("id start");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Complaints> complaintsForToday = findByDate(currentDate);
        int complaintNumber = complaintsForToday.size() + 1;
        String formattedComplaintNumber = String.format("%03d", complaintNumber);
        System.out.println("id return");
        return formattedDate + "_" + formattedComplaintNumber;
    }

    public List<Complaints> findByDate(LocalDate date) {
        System.out.println("date start");
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String regexPattern = "^" + formattedDate.replace("-", "\\-") + ".*"; // Adjust the regex pattern
        Query query = Query.query(Criteria.where("registrationDate").regex(regexPattern));
        System.out.println("date end");
        return mongoTemplate.find(query, Complaints.class);
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
    }

    public void sendComplaintMail(List<String> mailIds,Complaints complaints) {
        for(String mailId: mailIds){
            String subject = "Complaint " + complaints.getComplainerId() + ": " + complaints.getComplaintCriteria();
            String body = "Registration Date: " + complaints.getRegistrationDate() + "\n" +
                    "Zone: " + complaints.getZone() + "\n" +
                    "Building Name: " + complaints.getBuildingName() + "\n" +
                    "Location Details: " + complaints.getLocationDetails() + "\n" +
                    "Complaint Criteria: " + complaints.getComplaintCriteria() + "\n" +
                    "Description: " + complaints.getDescription() + "\n";
            System.out.println("sending email to "+mailId);
            emailService.sendSimpleMessage(mailId, subject, body);
        }
    }
}
