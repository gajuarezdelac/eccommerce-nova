package com.course.altanto.controller;

import static com.course.altanto.constant.FileConstant.FORWARD_SLASH;
import static com.course.altanto.constant.FileConstant.TEMP_PROFILE_IMAGE_BASE_URL;
import static com.course.altanto.constant.FileConstant.USER_FOLDER;
import static com.course.altanto.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.course.altanto.entity.HttpResponse;
import com.course.altanto.entity.User;
import com.course.altanto.entity.UserPrincipal;
import com.course.altanto.entity.dto.RecoveryPasswordDTO;
import com.course.altanto.entity.dto.ResetPasswordDTO;
import com.course.altanto.entity.dto.UserDTO;
import com.course.altanto.exception.EmailExistException;
import com.course.altanto.exception.EmailNotFoundException;
import com.course.altanto.exception.ExceptionGeneric;
import com.course.altanto.exception.NotAnImageFileException;
import com.course.altanto.exception.UserNotFoundException;
import com.course.altanto.exception.UsernameExistException;
import com.course.altanto.service.IUserService;
import com.course.altanto.utils.JWTTokenProvider;

@RestController
@RequestMapping("/user")
public class UserController {

	  public static final String EMAIL_SENT = "An email with a new password was sent to: ";
	  public static final String PASSWORD_RESET = "Contraseña restablecida correctamente";
	  public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
	  private AuthenticationManager authenticationManager;
	  private IUserService userService;
	  private JWTTokenProvider jwtTokenProvider;

	  @Autowired
	    public UserController(AuthenticationManager authenticationManager, IUserService userService, JWTTokenProvider jwtTokenProvider) {
	        this.authenticationManager = authenticationManager;
	        this.userService = userService;
	        this.jwtTokenProvider = jwtTokenProvider;
	  }
	  
	  
	  @PostMapping("/login")
	    public ResponseEntity<User> login(@RequestBody User user) {
	        authenticate(user.getUsername(), user.getPassword());
	        User loginUser = userService.findUserByUsername(user.getUsername());
	        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
	        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
	        return new ResponseEntity<>(loginUser, jwtHeader, OK);
	    }

	   @PostMapping("/register")
	    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
	        User newUser = userService.register(user.getNames(), user.getSurnames(), user.getUsername(), user.getPassword(), user.getGender(), user.getDateOfBirth());
	        return new ResponseEntity<>(newUser, OK);
   	   }
	   
	   @DeleteMapping("/desactivate-profile/{username}")
	   public ResponseEntity<User> desactivateProfile(@PathVariable("username") String username) throws UserNotFoundException {
		   User response = userService.desactiveProfile(username);
		   return new ResponseEntity<User>(response, HttpStatus.OK);
	   }
	   
	   @PostMapping("/add")
	    public ResponseEntity<User> addNewUser(@RequestParam("names") String firstName,
	                                           @RequestParam("surnames") String lastName,
	                                           @RequestParam("username") String username,
	                                           @RequestParam("role") String role,
	                                           @RequestParam("gender") String gender,
	                                           @RequestParam("dateOfBirth") Date dateOfBirth,
	                                           @RequestParam("isNonLocked") String isNonLocked,
	                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException, MessagingException {
	    	
	    	
	        User newUser = userService.addNewUser(firstName, lastName, username, role, gender, dateOfBirth, Boolean.parseBoolean(isNonLocked), profileImage);
	        return new ResponseEntity<>(newUser, OK);
	    }

	    @PostMapping("/update")
	    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
	                                       @RequestParam("names") String firstName,
	                                       @RequestParam("surnames") String lastName,
	                                       @RequestParam("username") String username,
	                                       @RequestParam("role") String role,
	                                       @RequestParam("gender") String gender,
                                           @RequestParam("dateOfBirth") Date dateOfBirth,
	                                       @RequestParam("isActive") String isActive,
	                                       @RequestParam("isNonLocked") String isNonLocked,
	                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
	        User updatedUser = userService.updateUser(currentUsername, firstName, lastName, username, role,gender, dateOfBirth, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
	        return new ResponseEntity<>(updatedUser, OK);
	    }
	    
	    
	    @PostMapping("/update-profile/{username}")
	    public ResponseEntity<User> updateProfile(@PathVariable("username") String username, @RequestBody UserDTO request) throws UserNotFoundException {
	    	User response = userService.updateProfile(username, request);
	    	return new ResponseEntity<User>(response, HttpStatus.OK);
	    }
	    
	    
	    @GetMapping("/find/{username}")
	    public ResponseEntity<User> getUser(@PathVariable("username") String username) throws UserNotFoundException {
	        User user = userService.findUserByUsername(username);
	        return new ResponseEntity<>(user, OK);
	    }

	    @GetMapping("/list")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userService.getUsers();
	        return new ResponseEntity<>(users, OK);
	    }
	    
	    @GetMapping("/paginate") 
		public ResponseEntity<Page<User>> paginate(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
	            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
	            @RequestParam(value = "username", defaultValue = "", required = false) String username,
	            @RequestParam(value = "names", defaultValue = "", required = false) String names,
	            @RequestParam(value = "surnames", defaultValue = "", required = false) String surnames) {
			Page<User> response = userService.getAllUsersPaginate(pageNo, pageSize, username, names, surnames);
			return new ResponseEntity<>(response, HttpStatus.OK);	
		}

	    @PostMapping("/recovery-password")
	    public ResponseEntity<HttpResponse> recoveryPassword(@RequestBody RecoveryPasswordDTO request) throws MessagingException, EmailNotFoundException {
	        userService.recoveryPassword(request.getEmail());
	        return response(OK, EMAIL_SENT + request.getEmail());
	    }
	    
	    @PostMapping("/reset-password")
	    public ResponseEntity<HttpResponse> resetPassword(@RequestBody ResetPasswordDTO request) throws MessagingException, EmailNotFoundException, ExceptionGeneric {
	    	userService.resetPassword(request.getPassword(), request.getEmail(), request.getToken());
	    	 return response(OK, PASSWORD_RESET);
	    }
	    
	    @DeleteMapping("/delete/{username}")
//	    @PreAuthorize("hasAnyAuthority('user:delete')")
	    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
	        userService.deleteUser(username);
	        return response(OK, USER_DELETED_SUCCESSFULLY);
	    }

	    @PostMapping("/updateProfileImage")
	    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
	        User user = userService.updateProfileImage(username, profileImage);
	        return new ResponseEntity<>(user, OK);
	    }

	    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
	    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
	        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
	    }

	    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
	    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
	        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try (InputStream inputStream = url.openStream()) {
	            int bytesRead;
	            byte[] chunk = new byte[1024];
	            while((bytesRead = inputStream.read(chunk)) > 0) {
	                byteArrayOutputStream.write(chunk, 0, bytesRead);
	            }
	        }
	        return byteArrayOutputStream.toByteArray();
	    }

	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }

	    private HttpHeaders getJwtHeader(UserPrincipal user) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
	        return headers;
	    }

	    private void authenticate(String username, String password) {
	        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    }

}
