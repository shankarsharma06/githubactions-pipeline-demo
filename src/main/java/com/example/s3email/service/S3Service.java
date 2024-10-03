package com.example.s3email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;


import java.io.File;
import java.nio.file.Paths;

@Service
public class S3Service {

    /*@Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-key}")
    private String secretKey;*/

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;
    
  

    /*private S3Client createS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }*/
    
    private S3Client createS3Client(AssumeRoleExample role) {
        return S3Client.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(role.roleBasedreturnCredentials().accessKeyId(), role.roleBasedreturnCredentials().secretAccessKey())))
                .build();
    }

    public File downloadFile(String key) {
    	
    	  AssumeRoleExample role = new AssumeRoleExample();
    	    
    	    System.out.println("Temporary Access Key: " + role.roleBasedreturnCredentials().accessKeyId());
    	    System.out.println("Temporary Secret Key: " + role.roleBasedreturnCredentials().secretAccessKey());
        S3Client s3Client = createS3Client(role);
        String localPath = "downloaded_" + key;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.getObject(getObjectRequest, Paths.get(localPath));
        return new File(localPath);
    }
}
