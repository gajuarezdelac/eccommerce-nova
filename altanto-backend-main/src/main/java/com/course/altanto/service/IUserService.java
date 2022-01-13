package com.course.altanto.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.User;
import com.course.altanto.exception.EmailExistException;
import com.course.altanto.exception.EmailNotFoundException;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.exception.UserNotFoundException;
import com.course.altanto.exception.UsernameExistException;

public interface IUserService {

	
	  User register(String firstName, String lastName, String username) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

	    List<User> getUsers();

	    User findUserByUsername(String username);

	    User findUserByEmail(String email);

	    User addNewUser(String firstName, String lastName, String username,  String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException;

	    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String role, boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

	    void deleteUser(String username) throws IOException;

	    void resetPassword(String email) throws MessagingException, EmailNotFoundException;

	    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
