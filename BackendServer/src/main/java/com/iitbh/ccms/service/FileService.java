package com.iitbh.ccms.service;

import com.iitbh.ccms.Utils;
import com.iitbh.ccms.model_db.FileDB;
import com.iitbh.ccms.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class FileService{
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String SaveImageFile(Resource body) {
        byte[] fileBytes;
        if (body instanceof ByteArrayResource) {
            ByteArrayResource byteArrayResource = (ByteArrayResource) body;
            fileBytes = byteArrayResource.getByteArray();
        } else {
            throw new IllegalArgumentException("Unsupported resource type");
        }

        String base64EncodedImage = Base64.getEncoder().encodeToString(fileBytes);
        String fileId = getFileUniqueId();
        FileDB fileDB = new FileDB();
        fileDB.setFileId(fileId);
        fileDB.setImageData(base64EncodedImage);
        fileRepository.save(fileDB);
        return fileId;
    }

    public String getFileUniqueId(){
        List<FileDB> list = fileRepository.findAll();
        List<String> fileIds = list.stream()
                .map(FileDB::getFileId)
                .toList();
//        List<String> fileIds = fileRepository.findAllFileIds();
        while (true) {
            String fileId = Utils.generateId(40);
            if (!fileIds.contains(fileId)) {
                return fileId;
            }
        }
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
