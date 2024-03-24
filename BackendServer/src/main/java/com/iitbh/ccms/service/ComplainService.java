package com.iitbh.ccms.service;

import com.iitbh.ccms.model.ComplainOverview;
import com.iitbh.ccms.model.ComplainSubmit;
import com.iitbh.ccms.model_db.Complains;
import com.iitbh.ccms.repository.ComplainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComplainService {
    private final ComplainRepository complainRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

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
        String id = "";
        while(true) {
            id = generateRandomId(40);
            for (Complains complains : list) {
                if(!id.equals(complains.getComplainId())){
                    Complains complainsWithId = new Complains();
                    complainsWithId.convertToComplains(complainSubmit,id);
                    complainRepository.save(complainsWithId);
                    return id;
                }
            }
        }
    }

    public String generateRandomId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public ComplainOverview getSingleComplain(String complainId){
        System.out.println("complain Id "+complainId);
        Complains complains = complainRepository.findComplainsByComplainId(complainId);
        System.out.println(complains.getComplain());
        System.out.println(complains.getComplainerName());
        System.out.println(complains.getDateTime());
        return complains.convertToComplainOverView();
    }

}

