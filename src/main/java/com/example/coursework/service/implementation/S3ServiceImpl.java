package com.example.coursework.service.implementation;



import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.coursework.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3client;
    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Override
    public void uploadFile(String keyName, MultipartFile file) throws IOException {
        try (var inputStream = file.getInputStream()) {
            var metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            var putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, metadata);
            var putObjectResult = s3client.putObject(putObjectRequest);
            log.info("File uploaded successfully. Metadata: " + putObjectResult.getMetadata());
        } catch (AmazonServiceException e) {
            log.error("AmazonServiceException: " + e.getMessage(), e);
        } catch (SdkClientException e) {
            log.error("SdkClientException: " + e.getMessage(), e);
        }
    }

    @Override
    public InputStream getFileAsStream(String keyName) {
        S3Object s3object = s3client.getObject(bucketName, keyName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        return inputStream;
    }
    public void createS3Bucket(String bucketName) {
        if(s3client.doesBucketExist(bucketName)) {
            log.info("Bucket name already in use. Try another name.");
            return;
        }
        s3client.createBucket(bucketName);
    }
}
