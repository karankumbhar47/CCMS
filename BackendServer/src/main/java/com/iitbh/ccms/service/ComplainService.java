package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model_db.Complains;
import com.iitbh.ccms.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainService {
    private final ComplainRepository complainRepository;

    @Autowired
    public ComplainService(ComplainRepository complainRepository) {
        this.complainRepository = complainRepository;
    }

    public List<ComplainOverview> getAllComplains(){
        List<ComplainOverview> returnList = new ArrayList<>();
        List<Complains> list = complainRepository.findAll();
        for(Complains complains: list){
            ComplainOverview complainOverview = new ComplainOverview();
            complainOverview.setComplainerId(complains.getComplainerId());
            complainOverview.setComplainerName(complains.getComplainerName());
            complainOverview.setComplain(complains.getComplain());
            complainOverview.setComplainId(complains.getComplainId());
            complainOverview.setTags(complains.getTags());
            complainOverview.setStatus(complains.getStatus());
            complainOverview.setSeverity(complains.getSeverity());
            complainOverview.setDateTime(complains.getDateTime());
            complainOverview.setLocation(complains.getLocation());
            returnList.add(complainOverview);
        }
        return returnList;
    }

}

