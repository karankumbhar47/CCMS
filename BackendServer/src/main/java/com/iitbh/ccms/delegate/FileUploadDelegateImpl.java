package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.UploadApiDelegate;
import com.iitbh.ccms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileUploadDelegateImpl implements UploadApiDelegate {
    private final FileService fileService;

    @Autowired
    public FileUploadDelegateImpl(FileService fileService) {
        this.fileService  = fileService;
    }

    @Override
    public ResponseEntity<String> uploadFile(Resource body) {
        String fileId = "";
        try{
            System.out.println("called try");
            fileId = fileService.SaveImageFile(body);
        }catch (Exception e){
            System.out.println("called catch "+e);
            fileId  = "null";
        }
        System.out.println("fileId "+fileId);
        return ResponseEntity.ok(fileId);
    }
}
