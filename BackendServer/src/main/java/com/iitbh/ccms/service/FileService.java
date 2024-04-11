package com.iitbh.ccms.service;

import com.iitbh.ccms.model_db.FileDB;
import com.iitbh.ccms.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.io.IOException;
import java.util.Base64;

@Service
public class FileService{
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String saveImageFile(String userType,MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Empty file");
        }

        try {
            String base64EncodedImage = Base64.getEncoder().encodeToString(file.getBytes());
            String fileId = getFileUniqueId(userType);
            FileDB fileDB = new FileDB();
            fileDB.setFileId(fileId);
            fileDB.setImageData(base64EncodedImage);
            fileRepository.save(fileDB);
            return fileId;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }


    public String getFileUniqueId(String userType){
        String fileId = "";
        fileId+= userType.equals("user") ? "USER_" : "RSVR_";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = dateFormat.format(new Date());
        long currentTimeMillis = System.currentTimeMillis();
        fileId += currentDate + "_" + currentTimeMillis;
        return fileId;

    }

    public Resource getFile(String fileId){
        FileDB fileDB = fileRepository.getFileDBByFileId(fileId);
        if (fileDB == null) {
            return null;
        }

        String base64Data = fileDB.getImageData();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        return new ByteArrayResource(decodedBytes);
//        FileDB fileDB = fileRepository.getFileDBByFileId(fileId);
//        System.out.println(fileDB.getFileId());
//        String base64Data = fileDB.getImageData();
//        System.out.println("imageData 1 "+base64Data +"imageData 1 ");
//        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
//        System.out.println(Arrays.toString(decodedBytes));
//        Resource response = new ByteArrayResource(decodedBytes);
//        System.out.println(response.exists());
//        return response;
    }

}
