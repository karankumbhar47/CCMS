package com.iitbh.ccms.utils;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model_db.Complaint;
import com.iitbh.ccms.model_db.LocationDB;
import com.iitbh.ccms.model_db.UserDetailsDB;
import com.iitbh.ccms.repository.ComplaintRepository;
import com.iitbh.ccms.repository.LocationRepository;
import com.iitbh.ccms.repository.UsersRepository;
import com.iitbh.ccms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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
    private final ComplaintRepository complaintRepository;
    private final UsersRepository usersRepository;


    @Autowired
    public ComplaintUtils(MongoTemplate mongoTemplate, LocationRepository locationRepository, EmailService emailService, ComplaintRepository complaintRepository, UsersRepository usersRepository) {
        this.mongoTemplate = mongoTemplate;
        this.locationRepository = locationRepository;
        this.emailService = emailService;
        this.complaintRepository = complaintRepository;
        this.usersRepository = usersRepository;
    }

    public String getUniqueComplaintId() {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<Complaint> complaintForToday = findByDate(currentDate);
        int complaintNumber = complaintForToday.size() + 1;
        String formattedComplaintNumber = String.format("%03d", complaintNumber);
        return formattedDate + "_" + formattedComplaintNumber;
    }

    public List<Complaint> findByDate(LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String regexPattern = "^" + formattedDate.replace("-", "\\-") + ".*"; // Adjust the regex pattern
        Query query = Query.query(Criteria.where("registrationDate").regex(regexPattern));
        return mongoTemplate.find(query, Complaint.class);
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

    public void sendComplaintMail(List<String> mailIds, Complaint complaint) {
        for(String mailId: mailIds){
            String subject = "Complaint " + complaint.getComplainerId() + ": " + complaint.getComplaintCriteria();
            String body = "Registration Date: " + complaint.getRegistrationDate() + "\n" +
                    "Zone: " + complaint.getZone() + "\n" +
                    "Building Name: " + complaint.getBuildingName() + "\n" +
                    "Location Details: " + complaint.getLocationDetails() + "\n" +
                    "Complaint Criteria: " + complaint.getComplaintCriteria() + "\n" +
                    "Description: " + complaint.getDescription() + "\n";
            System.out.println("sending email to "+mailId);
            emailService.sendSimpleMessage(mailId, subject, body);
        }
    }

    public int getTotalPages(int size) {
        long totalElements = (int) complaintRepository.count();
        return (int) Math.ceil((double) totalElements / size);
    }

    public List<ComplaintDetails> getComplaintPage(int pageNumber, int pageSize){
        Page<Complaint> complaintsPage = complaintRepository.findAll(PageRequest.of(pageNumber- 1, pageSize,Sort.by(Sort.Direction.DESC,"_id")));
        List<Complaint> list = complaintsPage.getContent();
        List<ComplaintDetails> returnList = new ArrayList<>();
        for(Complaint complains: list){
            ComplaintDetails complainOverview = complains.convertToComplainOverview();
            UserDetailsDB userDetailsDB = usersRepository.findByUserId(complains.getComplainerId());
            if(userDetailsDB != null){ complainOverview.setUserInfo(userDetailsDB.convertToUserDetails()); }
            returnList.add(complainOverview);
        }
        return returnList;
    }
}
