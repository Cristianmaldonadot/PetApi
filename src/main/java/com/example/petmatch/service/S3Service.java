package com.example.petmatch.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.petmatch.interfaces.IS3Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service implements IS3Service {

	private final S3Client s3Client;
	
	@Autowired
	public S3Service(S3Client s3Client) {
		this.s3Client = s3Client;
	}
	
	public String uploadFile(MultipartFile file)throws IOException{
		
		try {
			String fileName = file.getOriginalFilename();
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
					.bucket("imagenes-tienda")
					.acl(ObjectCannedACL.PUBLIC_READ)
					.key(fileName)
					.build();
			
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
			
			return "Archivo Subido";
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}
	
	public String deleteFile(String fileName) throws IOException {
		try {
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
					.bucket("imagenes-tienda")
					.key(fileName)
					.build();
			s3Client.deleteObject(deleteObjectRequest);
			System.out.println(fileName);
			return "Archivo Borrado correctamente";
		} catch (S3Exception e) {
			throw new IOException(e.getMessage());
		}
	}

}
