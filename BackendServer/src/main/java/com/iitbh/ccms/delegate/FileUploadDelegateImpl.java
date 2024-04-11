package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UploadApiDelegate;
import com.iitbh.ccms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadDelegateImpl implements UploadApiDelegate {
    private final FileService fileService;

    @Autowired
    public FileUploadDelegateImpl(FileService fileService) {
        this.fileService  = fileService;
    }



    @Override
    public ResponseEntity<String> uploadFile(String userType, MultipartFile imageFile) {
        String fileId = "";
        try{
            System.out.println("called try");
            fileId = fileService.saveImageFile(userType,imageFile);
        }catch (Exception e){
            System.out.println("called catch "+e);
            fileId  = "null";
        }
        System.out.println("fileId "+fileId);
        return ResponseEntity.ok(fileId);
    }

}
