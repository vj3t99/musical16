package com.musical16.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface IFileStorageService {

	public String storeFile(MultipartFile file);
    public Resource loadFileAsResource(String fileName);
    public Boolean deleteFile(String filename);
}
