package com.course.altanto.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.File;
import com.course.altanto.exception.NotAnImageFileException;

@Component
public interface IFileService {
	
	List<File> upload(List<MultipartFile> images) throws IOException, NotAnImageFileException;
	
}
