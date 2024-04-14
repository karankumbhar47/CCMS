package com.iitbh.ccms.utils;

import com.iitbh.ccms.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailUtils {
    private final EmailService emailService;
    private final ComplaintUtils complaintUtils;
    @Autowired
    public EmailUtils(ComplaintUtils complaintUtils, EmailService emailService){
        this.complaintUtils = complaintUtils;
        this.emailService = emailService;
    }


    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Kolkata")
    public void  sendDailyEmail() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        long acCount = complaintUtils.getCountOfTagsOnDate("AC", yesterday);
        long messCount= complaintUtils.getCountOfTagsOnDate("Mess", yesterday);
        long plumbingCount = complaintUtils.getCountOfTagsOnDate("Plumbing", yesterday);
        long electricalCount = complaintUtils.getCountOfTagsOnDate("Electrical", yesterday);

        String to = "karansunilk@iitbhilai.ac.in";
        String subject = "Daily Email";
        String text =  "Daily Complaint Summary:\n"
                + "AC Complaints: " + acCount+ "\n"
                + "Mess Complaints: " + messCount + "\n"
                + "Plumbing Complaints: " + plumbingCount + "\n"
                + "Electrical Complaints: " + electricalCount;
        System.out.println(text);
        try {
            emailService.sendSimpleMessage(to, subject, text);
        } catch (MailException e) {
            System.out.println("Failed to send daily email. MailException: " + e.getMessage());
            System.out.println("" + e.getCause());
        }
    }
}
