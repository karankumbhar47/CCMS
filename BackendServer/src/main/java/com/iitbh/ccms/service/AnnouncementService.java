package com.iitbh.ccms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.model_db.AnnouncementDoc;
import com.iitbh.ccms.repository.AnnouncementRepository;

@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<AnnouncementDoc> getAnnouncements(){
        return announcementRepository.findAll();
    }
}
