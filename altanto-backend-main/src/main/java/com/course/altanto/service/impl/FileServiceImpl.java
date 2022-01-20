package com.course.altanto.service.impl;

import static com.course.altanto.constant.FileConstant.DIRECTORY_CREATED;
import static com.course.altanto.constant.FileConstant.DOT;
import static com.course.altanto.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.course.altanto.constant.FileConstant.JPG_EXTENSION;
import static com.course.altanto.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static com.course.altanto.constant.FileConstant.PRODUCT_FOLDER;
import static com.course.altanto.constant.FileConstant.PRODUCT_IMAGE_PATH;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.altanto.entity.File;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.repository.IFileRepository;
import com.course.altanto.service.IFileService;


@Component
@Transactional
public class FileServiceImpl implements IFileService{
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private IFileRepository fileRepository;
	
	public FileServiceImpl(IFileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public List<File> upload(List<MultipartFile> images) throws IOException, NotAnImageFileException {
		
		List<File> list = new ArrayList<>();
		
		// Validamos que todos los archivos adjuntados sean de tipo imagen o descendientes de este.
		for(MultipartFile file : images) {
			if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(file.getContentType())) {
                throw new NotAnImageFileException(file.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
		}
		
		
		
		// Guardamos los archivos en el server y a nivel base de datos
		for(MultipartFile file :  images) {
		    File element = new File();
			File entity = saveProfileImage(element, file);
			list.add(entity);
		}
		
		return list;
	}

	
	private File saveProfileImage(File entity,MultipartFile profileImage) throws IOException, NotAnImageFileException {
         if (profileImage != null) {
            
        	final String uuid = UUID.randomUUID().toString().toLowerCase();
        	  
        	Path userFolder = Paths.get(PRODUCT_FOLDER + uuid).toAbsolutePath().normalize();
        	  
            if(!Files.exists(userFolder)) {
                  Files.createDirectories(userFolder);
                  LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
              
            Files.deleteIfExists(Paths.get(userFolder + uuid + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(uuid + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            entity.setRouteFile(setProfileImageUrl(uuid));
            entity.setNameFile(uuid);
            entity.setNameEntity(uuid);
            entity.setRegCreatedBy("TEST");
            entity.setRegDateCreated(new Date());
            fileRepository.save(entity);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
		return entity;
    }
	
	private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(PRODUCT_IMAGE_PATH + username + "/"
        + username + DOT + JPG_EXTENSION).toUriString();
    }

}
