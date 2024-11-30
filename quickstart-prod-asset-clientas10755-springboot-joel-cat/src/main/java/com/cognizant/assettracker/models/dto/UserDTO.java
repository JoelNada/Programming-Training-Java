package com.cognizant.assettracker.models.dto;


import com.cognizant.assettracker.models.enums.Role;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	@Id
	private int id;
	private String email;
	private String name;
	private List<Role> roles;

}

