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

	/**
	 * Just a simple method to test if the app endpoint is working.
	 * 
	 * @RequestMapping(value = "/sayhello", method = RequestMethod.GET)
	 * @param firstName
	 * @param lastName
	 * @return
	 * @throws Exception
	 */
	public String sayHello(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName)
			throws Exception {

		return "Hello " + firstName + " " + lastName + " " + awsS3Credentials.getS3Region();
	}

	/**
	 * Method exposes an endpoint to which a user can upload a file which this
	 * method then uploads to the S3 bucket associated with the S3 service instance
	 * bound to this app by the AWS service broker @RequestMapping("/uploadfile")
	 * 
	 * @param file
	 * @return
	 */
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
