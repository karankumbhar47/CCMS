package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.AnnouncementsApiDelegate;
import com.iitbh.ccms.model.Announcement;
import com.iitbh.ccms.service.GetAnnouncementService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AnnouncementDelegateApiImpl implements AnnouncementsApiDelegate {
    private final GetAnnouncementService getAnnouncement;

    public AnnouncementDelegateApiImpl(GetAnnouncementService service) {
        this.getAnnouncement = service;
    }

    @Override
    public ResponseEntity<List<Announcement>> getAnnouncements() {
        ArrayList<Announcement> dummy = new ArrayList<Announcement>();
        Announcement announcement = new Announcement();
        announcement.setId(1L);
        announcement.setTitle("First Dummy commit from server");
        announcement.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi ut nisl vel tortor bibendum commodo. Sed consequat, quam eget aliquam malesuada, tortor magna fringilla nulla, ac bibendum tellus tellus a lectus.");
        dummy.add(announcement);
        return ResponseEntity.ok(dummy);
    }

}
