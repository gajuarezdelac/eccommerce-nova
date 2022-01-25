package com.course.altanto.service.impl;

import static com.course.altanto.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.course.altanto.constant.FileConstant.DIRECTORY_CREATED;
import static com.course.altanto.constant.FileConstant.DOT;
import static com.course.altanto.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.course.altanto.constant.FileConstant.FORWARD_SLASH;
import static com.course.altanto.constant.FileConstant.JPG_EXTENSION;
import static com.course.altanto.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static com.course.altanto.constant.FileConstant.USER_FOLDER;
import static com.course.altanto.constant.FileConstant.USER_IMAGE_PATH;
import static com.course.altanto.constant.UserImplConstant.EMAIL_ALREADY_EXISTS;
import static com.course.altanto.constant.UserImplConstant.FOUND_USER_BY_USERNAME;
import static com.course.altanto.constant.UserImplConstant.NO_USER_FOUND_BY_EMAIL;
import static com.course.altanto.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;
import static com.course.altanto.enumeration.Role.ROLE_USER;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.altanto.entity.User;
import com.course.altanto.entity.UserPrincipal;
import com.course.altanto.entity.dto.UserDTO;
import com.course.altanto.enumeration.Role;
import com.course.altanto.exception.EmailExistException;
import com.course.altanto.exception.EmailNotFoundException;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.exception.UserNotFoundException;
import com.course.altanto.exception.UsernameExistException;
import com.course.altanto.repository.IUserRepository;
import com.course.altanto.service.IUserService;
import com.course.altanto.utils.EmailService;
import com.course.altanto.utils.LoginAttemptService;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements IUserService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private IUserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;
    private EmailService emailService;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   User user = userRepository.findUserByUsername(username);
	        if (user == null) {
	            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
	            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
	        } else {
	            validateLoginAttempt(user);
	            user.setLastLoginDateDisplay(user.getLastLoginDate());
	            user.setLastLoginDate(new Date());
	            userRepository.save(user);
	            UserPrincipal userPrincipal = new UserPrincipal(user);
	            LOGGER.info(FOUND_USER_BY_USERNAME + username);
	            return userPrincipal;
	        }
	}

	@Override
	public User register(String firstName, String lastName, String username, String password, String gender, Date dateOfBirth)
			throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
		
		  validateNewUsernameAndEmail(EMPTY, username);
	        User user = new User();
	        user.setNames(firstName);
	        user.setSurnames(lastName);
	        user.setUsername(username);
	        user.setJoinDate(new Date());
	        user.setPassword(encodePassword(password));
	        user.setGender(gender);
	        user.setDateOfBirth(dateOfBirth);
	        user.setActive(true);
	        user.setNotLocked(true);
	        user.setRole(ROLE_USER.name());
	        user.setAuthorities(ROLE_USER.getAuthorities());
	        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
	        userRepository.save(user);
	        return user;
		
	}

	@Override
	public List<User> getUsers() {
		  return userRepository.findAll();
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}
	
	@Override
	public User addNewUser(String firstName, String lastName, String username,  String role, String gender, Date dateOfBirth,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException,
			UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException {
		
		
		 validateNewUsernameAndEmail(EMPTY, username);
		  User user = new User();
	      String password = generatePassword();
	      System.out.println(password);
	      user.setNames(firstName);
	      user.setSurnames(lastName);
	      user.setJoinDate(new Date());
	      user.setUsername(username);
	      user.setPassword(encodePassword(password));
	      user.setGender(gender);
	      user.setDateOfBirth(dateOfBirth);
	      user.setActive(isActive);
	      user.setNotLocked(isNonLocked);
	      user.setRole(getRoleEnumName(role).name());
	      user.setAuthorities(getRoleEnumName(role).getAuthorities());
	      user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
	      userRepository.save(user);
	      saveProfileImage(user, profileImage);
	      LOGGER.info("New user password: " + password);
	      emailService.sendNewPasswordEmail(user.getNames(), password, user.getUsername());
	      
	      return user;  
	}

	@Override
	public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String role, String gender, Date dateOfBirth, boolean isNonLocked, boolean isActive, MultipartFile profileImage)
			throws UserNotFoundException, UsernameExistException, EmailExistException, IOException,
			NotAnImageFileException {
		  User currentUser = validateNewUsernameAndEmail(currentUsername, newUsername);
	        currentUser.setNames(newFirstName);
	        currentUser.setSurnames(newLastName);
	        currentUser.setUsername(newUsername);
	        currentUser.setActive(isActive);
	        currentUser.setGender(gender);
	        currentUser.setDateOfBirth(dateOfBirth);
	        currentUser.setNotLocked(isNonLocked);
	        currentUser.setRole(getRoleEnumName(role).name());
	        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
	        userRepository.save(currentUser);
	        saveProfileImage(currentUser, profileImage);
	        return currentUser;
	}

	@Override
	public void deleteUser(String username) throws IOException {
		User user = userRepository.findUserByUsername(username);
        Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
        FileUtils.deleteDirectory(new File(userFolder.toString()));
        userRepository.deleteById(user.getId());
	}

	@Override
	public void resetPassword(String newPassword, String email, String token) throws MessagingException, EmailNotFoundException, ExceptionGeneric {
	
		User user = userRepository.findUserByTokenAndUsername(token, email);
	
		if(user == null ) { 
	    	 throw new ExceptionGeneric("El token es incorrecto o bien ya fue utilizado.");
		}
		
	    if(user.getExpireToken().before(new Date())) {
	    	 throw new ExceptionGeneric("El token se encuentra expirado!");
	    }
	    
	    user.setToken("");
	    user.setPassword(encodePassword(newPassword));
	    
	    userRepository.save(user);
	}

	@Override
	public User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException,
			UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
		 User user = validateNewUsernameAndEmail(username, null);
	     saveProfileImage(user, profileImage);
	     return user;
	}
	
	
	// Others methods 
	private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            
        	if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            
        	
        	
            Path userFolder = Paths.get(USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
           
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(DIRECTORY_CREATED + userFolder);
            }
            
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + DOT + JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImageUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
        
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(USER_IMAGE_PATH + username + FORWARD_SLASH
        + username + DOT + JPG_EXTENSION).toUriString();
    }

    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(23);
    }

    private void validateLoginAttempt(User user) {
        if(user.isNotLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    
	private User validateNewUsernameAndEmail(String currentUsername, String newUsername) throws UserNotFoundException, UsernameExistException, EmailExistException{
		
	   User userByNewUsername = findUserByUsername(newUsername);
		 
	  if(StringUtils.isNotBlank(currentUsername)) { 
		 User currentUser = findUserByUsername(currentUsername);
	     if(currentUser == null) {
	    	 throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
	     }
	     
	     if(userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
	    	 throw new UsernameExistException(EMAIL_ALREADY_EXISTS);
	     }
	      
	     return currentUser;
	  } else {
		  
	      if(userByNewUsername != null) {
		    	 throw new UsernameExistException(EMAIL_ALREADY_EXISTS);
		  }
		  
		  return userByNewUsername;
	  }  
	}
	

	@Override
	public User updateProfile(String currentUsername, UserDTO request) throws UserNotFoundException {
		
		User element = validateUpdateUsername(currentUsername);
		element.setNames(request.getNames());
		element.setSurnames(request.getSurnames());
		element.setGender(request.getGender());
		element.setDateOfBirth(request.getDateOfBirth());
		element.setNumberPhone(request.getNumberPhone());
		element.setUsername(request.getUsername());
		userRepository.save(element);
		
		return element;
	}
	

	private User validateUpdateUsername(String currentUsername) throws UserNotFoundException {
		
		 User currentUser = findUserByUsername(currentUsername);
		
		  if(StringUtils.isNotBlank(currentUsername)) { 
			  if(currentUser == null) {
			    	 throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
			   }     
			}
		  
		return currentUser;
	}
	

	@Override
	public void recoveryPassword(String email) throws EmailNotFoundException, MessagingException {
		
		    User user = userRepository.findUserByUsername(email);
		    
	        if (user == null) {
	            throw new EmailNotFoundException(NO_USER_FOUND_BY_EMAIL + email);
	        }
	        
	        Calendar calendar = Calendar.getInstance();	
	        calendar.setTime(new Date()); 
	        calendar.add(Calendar.HOUR, 1);  
	        Date expireToken = calendar.getTime();
	        
	        String token = generatePassword();
	        user.setToken(token);
	        user.setExpireToken(expireToken);	        
	        userRepository.save(user);
	        
	        LOGGER.info("Token generate: " + token);
	        emailService.resetPassword(user.getNames(), token, user.getUsername());
	}

	@Override
	public User desactiveProfile(String currentUsername) throws UserNotFoundException {
		User element = validateUpdateUsername(currentUsername);
		element.setActive(false);		
		return element;
	}

	@Override
	public Page<User> getAllUsersPaginate(int pageNo, int pageSize, String username, String names, String surnames) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);   
		Page<User> response = userRepository.searchByFilters(username, names, surnames, pageable);	  
		return response;
	}

}
