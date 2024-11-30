package com.cognizant.assettracker.services.serviceimpl;


import com.cognizant.assettracker.controller.ExcelController;
import com.cognizant.assettracker.controller.NotificationController;
import com.cognizant.assettracker.models.dto.JwtResponseDTO;
import com.cognizant.assettracker.models.entity.UserLoginLogout;
import com.cognizant.assettracker.repositories.UserLoginLogoutRepository;
import com.cognizant.assettracker.services.UserLoginLogoutService;
import com.cognizant.assettracker.services.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserLoginLogoutServiceImpl implements UserLoginLogoutService {

	@Autowired
	private UserLoginLogoutRepository loginLogoutRepository;
	@Autowired
	private ExcelController excelController;

	@Autowired
	private NotificationController notificationController;

	public void logUserLogin(String username) {
		UserLoginLogout loginLogout = new UserLoginLogout();
		loginLogout.setUsername(username);
		loginLogout.setLoginTime(LocalDateTime.now());
		loginLogoutRepository.save(loginLogout);
	}

	public void logUserLogout(String username) {
		Optional<UserLoginLogout> optionalLoginLogout = loginLogoutRepository.findFirstByUsernameOrderByLoginTimeDesc(username);
		if (optionalLoginLogout.isPresent()) {
			UserLoginLogout loginLogout = optionalLoginLogout.get();
			loginLogout.setLogoutTime(LocalDateTime.now());

			// Calculate session duration
			Duration sessionDuration = Duration.between(loginLogout.getLoginTime(), loginLogout.getLogoutTime());
			loginLogout.setSessionDuration(sessionDuration);
			loginLogoutRepository.save(loginLogout);

		}
		excelController.cleanUp();
		notificationController.close();
	}

	public UserLoginLogout getUserLoginInfo(String username) {
		return loginLogoutRepository.findFirstByUsernameOrderByLoginTimeDesc(username).orElse(null);
	}
}

