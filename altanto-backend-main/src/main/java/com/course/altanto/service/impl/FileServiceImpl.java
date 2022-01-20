package com.course.altanto.service.impl;

import static com.course.altanto.constant.FileConstant.DIRECTORY_CREATED;
import static com.course.altanto.constant.FileConstant.DOT;
import static com.course.altanto.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.course.altanto.constant.FileConstant.JPG_EXTENSION;
import static com.course.altanto.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static com.course.altanto.constant.FileConstant.USER_FOLDER;
import static com.course.altanto.constant.FileConstant.USER_IMAGE_PATH;
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
		
		for(MultipartFile file :  images) {
		    File element = new File();
			File entity = saveProfileImage(element, file);
			list.add(entity);
		}
		
		return list;
	}

	
	
	private File saveProfileImage(File entity,MultipartFile profileImage) throws IOException, NotAnImageFileException {
         if (profileImage != null) {
            
        	if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            
        	final String uuid = UUID.randomUUID().toString().toLowerCase();
        	  
        	Path userFolder = Paths.get(USER_FOLDER + uuid).toAbsolutePath().normalize();
        	  
            if(!Files.exists(userFolder)) {
                  Files.createDirectories(userFolder);
                  LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
              
            Files.deleteIfExists(Paths.get(userFolder + uuid + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(uuid + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            entity.setRouteFile(setProfileImageUrl(uuid));
            entity.setConsecutive(uuid);
            entity.setNameFile(uuid);
            entity.setNameEntity(uuid);
            fileRepository.save(entity);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
         
        	
//        	// Establecemos la ruta de almacenamiento
//        	String filename = StringUtils.cleanPath(profileImage.getOriginalFilename());
//		    Path fileStorage = Paths.get(USER_FOLDER + "imagenes", filename).toAbsolutePath().normalize();
//        	
//            // En caso de que no exista el directorio
//            if(!Files.exists(fileStorage)) {
//                Files.createDirectories(fileStorage);
//                LOGGER.info(DIRECTORY_CREATED + fileStorage);
//            }
//             
//            // Remplazamos el archivo en caso de que se encuentre el mismo nombre.
//             copy(profileImage.getInputStream(), fileStorage.resolve(filename + DOT + JPG_EXTENSION), REPLACE_EXISTING);
//            //Files.copy(profileImage.getInputStream(), fileStorage.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
//             entity.setRouteFile(setProfileImageUrl(filename));
//             fileRepository.save(entity);
//             
//            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
		return entity;
        
    }
	
	private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + "/"
        + username + DOT + JPG_EXTENSION).toUriString();
    }

}
