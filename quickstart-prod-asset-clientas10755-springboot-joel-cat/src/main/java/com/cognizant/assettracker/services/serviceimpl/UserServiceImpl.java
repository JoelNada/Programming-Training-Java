package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.dto.UserDTO;
import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.models.enums.Role;
import com.cognizant.assettracker.models.exceptions.UserEmailExistsException;
import com.cognizant.assettracker.repositories.UserRepository;
import com.cognizant.assettracker.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	public List<UserDTO> getUsers(){
		logger.info("Fetching all users");
		List<UserDTO> userDTOList = userRepository.findAll()
				.stream()
				.map(this::userToUserDto)
				.collect(Collectors.toList());
		logger.debug("Fetched {} users", userDTOList.size());
		return userDTOList;
	}

	public UserDTO userToUserDto(User user){
		UserDTO userDto = new UserDTO();
		userDto = this.modelMapper.map(user, UserDTO.class);
		logger.debug("Mapped User to UserDTO: {}", userDto);
		return userDto;
	}
	public User createUser(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<Role> roles = Arrays.asList(Role.NEW_USER);
		user.setRoles(roles);
		Optional<User> userExists =  userRepository.findByEmail(user.getEmail());
		if(userExists.isPresent()){
			throw new UserEmailExistsException("Email already Exists! please choose another Email");
		}
		logger.info("Creating a new user: {}", user.getUsername());
		return userRepository.save(user);
	}

	public List<UserDTO> getNewUsers(){
		logger.info("Fetching new users");
		return userRepository.findByRole()
				.stream()
				.map(this::userToUserDto)
				.collect(Collectors.toList());
	}

	public String getEmployeeId(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication!= null & authentication.isAuthenticated()){
			User loggedInUser = (User) authentication.getPrincipal();
			logger.debug("Getting employee ID for user: {}",loggedInUser.getUsername());
			return loggedInUser.getEmployeeId();
		}
		return null;
	}

	public String getUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		logger.debug("Getting username: {}", username);
//		MDC.put("userEmail",username);
		return username;
	}


	public void deleteUser(String id) {
		logger.info("Deleting user with ID: {}", id);
		userRepository.deleteById(id);
		logger.info("User with ID {} deleted", id);
	}
}