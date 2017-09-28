package edu.unh.comp851;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

public class S3Example {

	public Properties loadProperties() throws IOException {
		InputStream in = getClass().getResourceAsStream("aws.properties");
		Properties prop = new Properties();
		prop.load(in);
		in.close();
		return prop;
	}

	public static void main(String[] args) {
		S3Example example = new S3Example();
		Properties prop = null;
		try {
			prop = example.loadProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(prop.size() + "");
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(prop.getProperty("KEY"), prop.getProperty("SECRET"));
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();
		List<Bucket> buckets = s3.listBuckets();
		System.out.println("Your Amazon S3 buckets are:");
		for (Bucket b : buckets) {
			System.out.println("* " + b.getName());
		}
	}

}
