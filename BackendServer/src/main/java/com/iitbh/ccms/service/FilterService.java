package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplaintDetails;
import com.iitbh.ccms.model.ComplaintPage;
import com.iitbh.ccms.model_db.Complaint;
import com.iitbh.ccms.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    private final ComplaintRepository complaintRepository;
    @Autowired
    public FilterService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public ComplaintPage getFilteredComplaint(List<String> tags, String totime, String fromtime, String status, int pageNumber, int pageSize) {
//        List<ComplaintDetails> allComplaints = complaintRepository.findAll().stream().map(Complaint::convertToComplainOverview).collect(Collectors.toList());
//        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, Sort.by(Sort.Direction.ASC, "_id"));
        List<Complaint> complaintPage = complaintRepository.findAll();

        List<ComplaintDetails> complaintDetailsList = complaintPage.stream()
                .map(Complaint::convertToComplainOverview)
                .collect(Collectors.toList());
        List<ComplaintDetails> tagFilteredComplaints = filterComplaintByTag(complaintDetailsList, tags);
        List<ComplaintDetails> filteredByDate = new ArrayList<>();
        List<ComplaintDetails> filteredComplaints = new ArrayList<>();

        for (ComplaintDetails complaint : tagFilteredComplaints) {
            if (dateIsInBetween(complaint.getComplaintInfo().getRegistrationDate(), totime, fromtime)) {
                filteredByDate.add(complaint);
            }
        }
        if (status == null || status.isEmpty()){
            filteredComplaints = filteredByDate;
        } else {
            for (ComplaintDetails complaint : filteredByDate) {
                if (complaint.getComplaintInfo().getStatus().toString().equals(status)) {
                    filteredComplaints.add(complaint);
                }
            }
        }
//        int s = 0;
//        if (tags == null || tags.isEmpty()){
//            List<Complaint> complaints = complaintRepository.findByStatus(status);
//            if (status == null || status.isEmpty()) {
//                complaints = complaintRepository.findAll();
//            }
//            for (Complaint complaint: complaints){
//                if (dateIsInBetween(complaint.getRegistrationDate(), totime, fromtime)){
//                    s++;
//                }
//            }
//        }
//        else {
//            for (String tag: tags){
//                List<Complaint> complaints = complaintRepository.findByBuildingNameAndStatus(tag, status);
//                if (status == null || status.isEmpty()) {
//                    complaints = complaintRepository.findAll();
//                }
//                for (Complaint complaint: complaints){
//                    if (dateIsInBetween(complaint.getRegistrationDate(), totime, fromtime)){
//                        s++;
//                    }
//                }
//            }
//        }
        ComplaintPage filteredComplaintPage = new ComplaintPage();
        filteredComplaintPage.setTotalPages(1);
        filteredComplaintPage.setComplaintList(filteredComplaints);
        System.out.println(filteredComplaints);
        return filteredComplaintPage;
    }

    public List<ComplaintDetails> filterComplaintByTag(List<ComplaintDetails> complaints, List<String> tags) {
        List<ComplaintDetails> filtered = new ArrayList<>();
        if (tags==null || tags.isEmpty()) {
            return complaints;
        }
        for (ComplaintDetails complaint : complaints) {
            if (tags.stream().anyMatch(tag -> tag.equals(complaint.getComplaintInfo().getBuildingName()))) {
                filtered.add(complaint);
            }
        }
        return filtered;
    }

    public boolean dateIsInBetween(String date, String todate, String fromdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        if (todate==null || todate.isEmpty()) {
            todate = "9999-01-01";
        }
        if (fromdate==null || fromdate.isEmpty()) {
            fromdate = "0011-01-01";
        }
        LocalDate regDate = LocalDate.parse(date.substring(0, 10), format);
        LocalDate toDate = LocalDate.parse(todate, formatter);
        LocalDate fromDate = LocalDate.parse(fromdate, formatter);
        return (regDate.isEqual(fromDate) || regDate.isAfter(fromDate))
                && (regDate.isEqual(toDate) || regDate.isBefore(toDate));
    }
}
