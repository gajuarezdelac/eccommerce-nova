package com.course.altanto.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.User;
import com.course.altanto.entity.dto.UserDTO;
import com.course.altanto.exception.EmailExistException;
import com.course.altanto.exception.EmailNotFoundException;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.exception.UserNotFoundException;
import com.course.altanto.exception.UsernameExistException;

@Component
public interface IUserService {
	
	    User register(String firstName, String lastName, String username, String password, String gender, Date dateOfBirth) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

	    List<User> getUsers();
	    
	    Page<User> getAllUsersPaginate(int pageNo, int pageSize);

	    User findUserByUsername(String username);
  
	    User desactiveProfile(String username) throws UserNotFoundException;
	    
	    User updateProfile(String currentUsername, UserDTO request) throws UserNotFoundException;

	    User addNewUser(String firstName, String lastName, String username,  String role, String gender, Date dateOfBirth,  boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException;

	    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String role, String gender, Date dateOfBirth, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

	    void deleteUser(String username) throws IOException;

	    void resetPassword(String newPassword, String email, String token) throws MessagingException, EmailNotFoundException, ExceptionGeneric;
	    
	    void recoveryPassword(String token) throws MessagingException, EmailNotFoundException;

	    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
