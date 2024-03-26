package com.iitbh.ccms.delegate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iitbh.ccms.api.AnnouncementsApiDelegate;
import com.iitbh.ccms.model.Announcement;
import com.iitbh.ccms.model.NewAnnouncement;
import com.iitbh.ccms.model_db.AnnouncementDoc;
import com.iitbh.ccms.service.AnnouncementService;

@Service
public class AnnouncementDelegateApiImpl implements AnnouncementsApiDelegate {
    private final AnnouncementService announcementService;

    public AnnouncementDelegateApiImpl(AnnouncementService service) {
        this.announcementService = service;
    }

    @Override
    public ResponseEntity<List<Announcement>> getAnnouncements() {
        List<AnnouncementDoc> docs = announcementService.getAnnouncements();
        ArrayList<Announcement> res = new ArrayList<Announcement>();
        docs.forEach((AnnouncementDoc doc) -> {
            res.add(doc.toApiAnnouncement());
        });
        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<Boolean> newAnnouncement(NewAnnouncement newAnnouncement) {
        boolean res = announcementService.newAnnouncement(newAnnouncement);
        return ResponseEntity.ok(res);
    }

}
