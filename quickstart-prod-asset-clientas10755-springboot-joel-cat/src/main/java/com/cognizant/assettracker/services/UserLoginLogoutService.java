package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.entity.UserLoginLogout;

public interface UserLoginLogoutService {
	public void logUserLogin(String username);
	public void logUserLogout(String username);
	public UserLoginLogout getUserLoginInfo(String username);
}
