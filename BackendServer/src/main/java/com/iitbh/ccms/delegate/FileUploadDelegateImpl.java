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
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        return ResponseEntity.ok(fileService.SaveImageFile(file));
    }
}
