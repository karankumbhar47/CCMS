package com.iitbh.ccms.utils;

import com.iitbh.ccms.model.ComplaintInfo;
import com.iitbh.ccms.model_db.Complaint;
import com.iitbh.ccms.repository.ComplaintRepository;
import com.iitbh.ccms.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ComplaintScheduler {

    private final ComplaintRepository complaintRepository;

    @Autowired
    public ComplaintScheduler(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Scheduled(cron = "0 37 2 * * *", zone = "Asia/Kolkata")
    public void autoCloseComplaints() {
        List<Complaint> partiallyClosedComplaints = complaintRepository.findComplaintsByStatus(ComplaintInfo.StatusEnum.PARTIALLYCLOSE.getValue());
        LocalDate currentDate = LocalDate.now();

        for (Complaint complaint : partiallyClosedComplaints) {
            LocalDate closingDate = parseClosingDate(complaint.getComplaintId());
            System.out.println("get all complain "+complaint.getComplaintId());

            if(complaint.getStatus().equals(ComplaintInfo.StatusEnum.PARTIALLYCLOSE.getValue())) {
                if (currentDate.isAfter(closingDate.plusDays(3))) {
                    System.out.println("get remove "+complaint.getComplaintId());
                    System.out.println("get remove "+complaint.getResolutionDate());
                    complaint.setStatus(ComplaintInfo.StatusEnum.CLOSE.getValue());
                    complaintRepository.save(complaint);
                }
            }
        }
    }

    private LocalDate parseClosingDate(String complaintId) {
        String datePart = complaintId.substring(0, 8);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(datePart, formatter);
    }

}
