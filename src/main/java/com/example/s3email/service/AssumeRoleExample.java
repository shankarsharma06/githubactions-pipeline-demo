package com.example.s3email.service;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;

public class AssumeRoleExample {

  public AwsSessionCredentials roleBasedreturnCredentials() {
        // Create an STS client
	  StsClient stsClient = StsClient.builder()
              .region(Region.EU_WEST_1)  // Set the region explicitly here
              .build();

        // Define the role ARN and session name
        String roleArn = "arn:aws:iam::444461369897:role/ods-ec2-ssm-role";  // Replace with your role ARN
        String sessionName = "my-session";  // A custom session name

        // Create the AssumeRoleRequest
        AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName(sessionName)
                .build();

        // Call STS to assume the role
        AssumeRoleResponse assumeRoleResponse = stsClient.assumeRole(assumeRoleRequest);

        // Extract the temporary credentials
        AwsSessionCredentials temporaryCredentials = AwsSessionCredentials.create(
                assumeRoleResponse.credentials().accessKeyId(),
                assumeRoleResponse.credentials().secretAccessKey(),
                assumeRoleResponse.credentials().sessionToken()
        );

        System.out.println("Temporary Access Key: " + temporaryCredentials.accessKeyId());
        System.out.println("Temporary Secret Key: " + temporaryCredentials.secretAccessKey());
        System.out.println("Temporary Session Token: " + temporaryCredentials.sessionToken());
		return temporaryCredentials;

        // You can now use these credentials to make API calls using other AWS SDK clients
  }
}
