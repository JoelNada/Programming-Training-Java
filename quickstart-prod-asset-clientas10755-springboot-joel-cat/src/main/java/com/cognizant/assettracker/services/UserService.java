package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.dto.UserDTO;
import com.cognizant.assettracker.models.entity.User;

import java.util.List;

public interface UserService {

    public List<UserDTO> getUsers();
    public UserDTO userToUserDto(User user);
    public User createUser(User user);

    public List<UserDTO> getNewUsers();

    public String getEmployeeId();

    public String getUser();

   public void deleteUser(String id);
}
