package com.cognizant.assettracker.controller;

import com.cognizant.assettracker.models.dto.JwtRequestDTO;
import com.cognizant.assettracker.models.dto.JwtResponseDTO;
import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.models.exceptions.UnauthorizedRoleException;
import com.cognizant.assettracker.models.exceptions.UserEmailExistsException;
import com.cognizant.assettracker.security.JwtHelper;
import com.cognizant.assettracker.services.UserLoginLogoutService;
import com.cognizant.assettracker.services.UserService;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserLoginLogoutService userLoginLogoutService;

	private String loggedInUsername;

	@PostMapping("/login")//This api is used to authenticate user.
	public ResponseEntity<JwtResponseDTO> login(@RequestBody JwtRequestDTO request,@RequestParam String role) throws Exception {
		logger.info("Received login request for user with email: {}", request.getEmail());
		this.doAuthenticate(request.getEmail(), request.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		if(hasValidRole(userDetails,role)) {
			String token = this.helper.generateToken(userDetails, role);
			JwtResponseDTO response = JwtResponseDTO.builder()
					.jwtToken(token)
					.username(userDetails.getUsername())
					.roles(new String[]{role})
					.build();
			logger.debug("Login successful for user with email: {}", request.getEmail());

			loggedInUsername = userDetails.getUsername();
			userLoginLogoutService.logUserLogin(request.getEmail());
			return new ResponseEntity<>(response, HttpStatus.OK);

		}else {
			logger.warn("Unauthorized login attempt for user with email: {}", request.getEmail());
			return (ResponseEntity<JwtResponseDTO>) ResponseEntity.status(HttpStatus.UNAUTHORIZED);
	}
	}
private boolean hasValidRole(UserDetails userDetails, String role) throws Exception {
	if(!userDetails.getAuthorities().stream().anyMatch(authority->authority.getAuthority().equals("ROLE_"+role))){
		logger.warn("User does not have the required role: {}", role);
		throw new UnauthorizedRoleException("UNAUTHORIZED ROLE");
	}
	else
		return true;
}
	private void doAuthenticate(String email, String password) {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {

			manager.authenticate(authentication);
			logger.debug("Authentication successful for user with email: {}", email);
		} catch (BadCredentialsException e) {
			logger.warn("Invalid Username or Password for user with email: {}", email);
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}

	}


	@PostMapping("/register")//This api is used to register new
	public User createUser(@RequestBody User user){
		try{
		logger.info("Received registration request for user: {}", user.getUsername());
		logger.debug("User registered successfully: {}", user.getUsername());
		return userService.createUser(user);}
		catch (UserEmailExistsException e){
			throw new UserEmailExistsException("User already exists! Please try another Email");
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		// Log logout time and calculate session duration
		userLoginLogoutService.logUserLogout(userService.getUser());
		return ResponseEntity.ok("Logout successful");
	}
}
