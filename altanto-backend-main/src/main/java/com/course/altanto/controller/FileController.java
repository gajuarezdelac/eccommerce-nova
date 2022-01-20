package com.course.altanto.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.course.altanto.entity.File;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.service.IFileService;
@RestController
@RequestMapping("/file")
public class FileController {

	  
	@Autowired
	private IFileService service;
	
	
   @PostMapping("/upload")
   public ResponseEntity<List<File>> uploadFilesND(@RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException, SAXException, ParserConfigurationException, NotAnImageFileException  {
		 List<File> response = service.upload(files);
	       return new ResponseEntity<>(response, HttpStatus.OK);	
   }
   
	 
}
