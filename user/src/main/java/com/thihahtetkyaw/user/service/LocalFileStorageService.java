package com.thihahtetkyaw.user.service;

import com.thihahtetkyaw.user.exception.FileUploadException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

@Service
public class LocalFileStorageService implements FileStorageService{

    @Override
    public String save(MultipartFile photo, String id, String storagePath) {
        var extension = getExtension(photo);
        try {
            var storageFolder = ResourceUtils.getFile(storagePath);
            if(!storageFolder.exists()){
                storageFolder.mkdirs();
            }
            var file = Path.of(storageFolder.getAbsolutePath(), id + "." + extension);
            Files.copy(photo.getInputStream(), file, StandardCopyOption.REPLACE_EXISTING);
            var fileName = file.toString();
            String[] result;
            if(fileName.contains("\\")){
                result = fileName.split("\\\\");
            }else{
                result = fileName.split("/");
            }
            return "/" + result[result.length - 2] + "/" + result[result.length - 1];

        } catch (IOException e) {
            throw new FileUploadException(e.getMessage());
        }
    }
}
