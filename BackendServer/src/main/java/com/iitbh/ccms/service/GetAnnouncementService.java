package com.iitbh.ccms.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.iitbh.ccms.model.Announcement;

@Service
public class GetAnnouncementService {

    public List<Announcement> getAnnouncements(){
        return new ArrayList<>();
    }
}
