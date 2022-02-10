package com.course.altanto.controller;

import static com.course.altanto.constant.FileConstant.FORWARD_SLASH;
import static com.course.altanto.constant.FileConstant.PRODUCT_FOLDER;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
   public ResponseEntity<List<File>> uploadFilesND(@RequestParam(value = "files", required = true) List<MultipartFile> files) throws IOException, SAXException, ParserConfigurationException, NotAnImageFileException  {
		 List<File> response = service.upload(files);
	       return new ResponseEntity<>(response, HttpStatus.OK);	
   }
   
   @GetMapping(path = "/image/{folder}/{fileName}", produces = IMAGE_JPEG_VALUE)
   public byte[] getProfileImage(@PathVariable("folder") String username, @PathVariable("fileName") String fileName) throws IOException {
       return Files.readAllBytes(Paths.get(PRODUCT_FOLDER + username + FORWARD_SLASH + fileName));
   }   
	 
}
