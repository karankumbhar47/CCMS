package com.iitbh.ccms.service;

import com.iitbh.ccms.Utils;
import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
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
            ComplainOverview complainOverview = complains.convertToComplainOverView();
            returnList.add(complainOverview);
        }
        return returnList;
    }

    public String SubmitComplain(ComplainSubmit complainSubmit){
        List<Complains> list =  complainRepository.findAll();
        while(true) {
            String complainId = getUniqueComplainId();
            for (Complains complains : list) {
                if(!complainId.equals(complains.getComplainId())){
                    Complains complainsWithId = new Complains();
                    complainsWithId.convertToComplains(complainSubmit,complainId);
                    complainRepository.save(complainsWithId);
                    return complainId;
                }
            }
        }
    }

    public ComplainOverview getSingleComplain(String complainId){
        Complains complains = complainRepository.findComplainsByComplainId(complainId);
        System.out.println(complains.getComplain());
        System.out.println(complains.getComplainerName());
        System.out.println(complains.getDateTime());
        return complains.convertToComplainOverView();
    }

    public String getUniqueComplainId(){
        List<Complains> list =  complainRepository.findAll();
        List<String> complainIds = list.stream()
                .map(Complains::getComplainId)
                .toList();
        while (true) {
            String complainId = Utils.generateId(40);
            if (!complainIds.contains(complainId)) {
                return complainId;
            }
        }
    }

}

