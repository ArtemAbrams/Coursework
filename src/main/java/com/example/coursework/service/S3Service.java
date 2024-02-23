package com.example.coursework.service;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface S3Service {

    void uploadFile(String keyName, MultipartFile file) throws IOException;

    InputStream getFileAsStream(String keyName);

}
