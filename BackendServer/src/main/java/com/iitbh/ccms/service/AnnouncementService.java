package com.iitbh.ccms.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model.NewAnnouncement;
import com.iitbh.ccms.model_db.AnnouncementDoc;
import com.iitbh.ccms.repository.AnnouncementRepository;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    private static DateTimeFormatter idTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<AnnouncementDoc> getAnnouncements() {
        return announcementRepository.findAll();
    }

    public boolean newAnnouncement(NewAnnouncement announcement) {
        LocalDateTime currentTime = LocalDateTime.now();
        Long id;

        if(announcement.getTitle().isEmpty()){
            return false;
        }

        if(announcement.getTime().isEmpty()){
            announcement.setTime(timeFormatter.format(currentTime));
        }

        while (true) {
            try {
                id = Long.parseLong(currentTime.format(idTimeFormatter));
            } catch (NumberFormatException e) {
                System.err.println("[AnnouncementService] Failed to parse formatted date as long");
                System.err.println(e);
                id = 0L;
            }
            /* Check if the id already exists */
            AnnouncementDoc newDoc = new AnnouncementDoc();

            newDoc.id = id;
            newDoc.title = announcement.getTitle();
            newDoc.description = announcement.getDescription();
            newDoc.time = announcement.getTime();

            try {
                announcementRepository.save(newDoc);
                return true;
            } catch (Exception e) {
                System.err.println("[AnnouncementService] Failed to save doc");
                System.err.println(e);
                return false;
            }
        }
    }
}
