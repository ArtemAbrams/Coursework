package com.example.coursework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {

    void uploadFile(String keyName, MultipartFile file) throws IOException;
    InputStream getFileAsStream(String keyName);
    void deleteFile(String keyName);
    void createS3Bucket(String bucketName);
    public boolean isFileExist(String keyName);
}
