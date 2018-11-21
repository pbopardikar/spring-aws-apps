package io.pivotal.awss3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

@SpringBootApplication
@RestController
public class Awss3Application {

	@Autowired
	private AwsS3Credentials awsS3Credentials;

	public static void main(String[] args) {
		SpringApplication.run(Awss3Application.class, args);
	}

	@RequestMapping(value = "/sayhello", method = RequestMethod.GET)
	public String sayHello(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName)
			throws Exception {

		return "Hello " + firstName + " " + lastName + " " + awsS3Credentials.getS3Region();
	}

	@RequestMapping("/uploadfile")
	public boolean upload(@RequestParam("file") MultipartFile file) {

		boolean success = false;
		try {
			AWSCredentials credentials = new BasicAWSCredentials(awsS3Credentials.getS3AwsAccessKeyId(),
					awsS3Credentials.getS3AwsSecretAccessKey());
			AmazonS3 s3client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(awsS3Credentials.getS3Region()).build();

			final String fileName = file.getOriginalFilename();
			byte[] fileBytes = file.getBytes();
System.out.println("~~~~~~~~~~~~~~~~~~~~"+fileBytes.length+"~~~~~~~~~~~~~~~~~~");
System.out.println("~~~~~~~~~~~~~~~~~~~~"+awsS3Credentials.getBucketName()+"~~~~~~~~~~~~~~~~~~");
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(fileBytes.length);
			s3client.putObject(awsS3Credentials.getBucketName(), fileName, file.getInputStream(), metadata);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
