package io.pivotal.awss3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("vcap.services.my-aws-s3.credentials")
public class AwsS3Credentials {

	private String s3AwsAccessKeyId;
	private String s3AwsSecretAccessKey;
	private String bucketArn;
	private String bucketName;
	private String loggingBucketName;
	private String s3Region;

	public String getS3AwsAccessKeyId() {
		return s3AwsAccessKeyId;
	}

	public void setS3AwsAccessKeyId(String s3AwsAccessKeyId) {
		this.s3AwsAccessKeyId = s3AwsAccessKeyId;
	}

	public String getS3AwsSecretAccessKey() {
		return s3AwsSecretAccessKey;
	}

	public void setS3AwsSecretAccessKey(String s3AwsSecretAccessKey) {
		this.s3AwsSecretAccessKey = s3AwsSecretAccessKey;
	}

	public String getBucketArn() {
		return bucketArn;
	}

	public void setBucketArn(String bucketArn) {
		this.bucketArn = bucketArn;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getLoggingBucketName() {
		return loggingBucketName;
	}

	public void setLoggingBucketName(String loggingBucketName) {
		this.loggingBucketName = loggingBucketName;
	}

	public String getS3Region() {
		return s3Region;
	}

	public void setS3Region(String s3Region) {
		this.s3Region = s3Region;
	}

}
