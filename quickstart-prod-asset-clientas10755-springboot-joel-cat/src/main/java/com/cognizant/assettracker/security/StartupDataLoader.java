package com.cognizant.assettracker.security;

import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.models.enums.Role;
import com.cognizant.assettracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartupDataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		if (userRepository.existsByEmployeeIdOrEmail("201007", "Amexpmo@cognizant.com")) {
			return;
		}

		// Create user and roles
		User user = new User();
		user.setEmployeeId("201007");
		user.setEmail("dev_user@cognizant.com");
		user.setName("Dev User");
		user.setPassword("$2a$10$b3YFzG7LanMxEjd.g9zoq.ENHAZCWhLFeZaRj3IeZBvtf1aNWlkzG");
		user.setRoles(Arrays.asList(Role.PMO, Role.EPL, Role.ESA_PM));

		userRepository.save(user);
	}
}
