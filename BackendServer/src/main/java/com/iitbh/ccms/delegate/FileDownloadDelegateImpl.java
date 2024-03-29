package com.iitbh.ccms.delegate;

import com.iitbh.ccms.api.DownloadApiDelegate;
import com.iitbh.ccms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class FileDownloadDelegateImpl  implements DownloadApiDelegate {
    private final FileService fileService;

    @Autowired
    public FileDownloadDelegateImpl(FileService fileService) {
        this.fileService  = fileService;
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileId) {
        System.out.println(fileId);
        return ResponseEntity.ok(fileService.getFile(fileId));
    }
}

