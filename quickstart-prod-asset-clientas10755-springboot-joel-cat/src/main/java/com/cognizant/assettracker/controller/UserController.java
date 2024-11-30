package com.cognizant.assettracker.controller;


import com.cognizant.assettracker.models.dto.HomePageDTO;
import com.cognizant.assettracker.models.dto.UserDTO;
import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.models.enums.Role;
import com.cognizant.assettracker.repositories.UserRepository;
import com.cognizant.assettracker.services.NotificationsService;
import com.cognizant.assettracker.services.serviceimpl.AmexEmployeeDetailServiceImpl;
import com.cognizant.assettracker.services.EPLService;
import com.cognizant.assettracker.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AmexEmployeeDetailServiceImpl amexEmployeeDetailDao;

	@Autowired
	private EPLService eplService;

	@Autowired
	NotificationsService notificationsService;

	@GetMapping("/get/all") //This api returns all the users info.
	@PreAuthorize("hasRole('PMO')")
	public List<UserDTO> getUsers(){
		logger.info("Fetching all users");
		List<UserDTO> userDTOList = userService.getUsers();
		logger.debug("Fetched {} users", userDTOList.size());
		return userDTOList;
	}

	@PutMapping("/{email}/role")//This api takes email in url and pass the roles in array to overwrite the role.
	@PreAuthorize("hasRole('PMO')")
	public ResponseEntity<String> changeUserRole(@PathVariable String email,@RequestParam (required = true) String action, @RequestBody List<Role> role){
		logger.info("Changing roles for user with email: {}", email);
		Optional<User> user =  userRepository.findByEmail(email);
		if (user.isEmpty()){
			throw new UsernameNotFoundException("User not found.");
		}
		User user1= user.get();
		if ("add".equalsIgnoreCase(action)){
			user1.getRoles().addAll(role);
			logger.info("Added roles: {} to user: {}", role, email);
			notificationsService.userRoleChangeNotification(email, role.toString(), "Add");
		} else if ("remove".equalsIgnoreCase(action)) {
			user1.getRoles().removeAll(role);
			logger.info("Removed roles: {} from user: {}", role, email);
			notificationsService.userRoleChangeNotification(email, role.toString(), "Remove");
		}
		userRepository.save(user1);
		logger.info("Role Updated Successfully for user: {}", email);
		return ResponseEntity.ok("Role Updated Successfully");
	}

	@GetMapping("/role/newusers") //This api returns list of new users.
	@PreAuthorize("hasRole('PMO')")
	public List<UserDTO> getNewUsers(){
		logger.info("Fetching new users");
		List<UserDTO> newUsers = userService.getNewUsers();
		logger.debug("Fetched {} new users", newUsers.size());
		return newUsers;
	}

	@DeleteMapping("/deleteuser/{Id}")
	public String deleteUser(@PathVariable("Id") String Id){
		try {
			logger.info("Deleting user with ID: {}", Id);
			userService.deleteUser(Id);
			logger.info("User with ID {} deleted", Id);
			return "Deleted Successfully";
		} catch (Exception e) {
			logger.error("Error while deleting user: {}", e.getMessage(), e);
			return "Error occurred while deleting user.";
		}
	}
}