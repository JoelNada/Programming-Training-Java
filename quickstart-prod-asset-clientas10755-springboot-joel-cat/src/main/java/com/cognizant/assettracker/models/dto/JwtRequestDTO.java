package com.cognizant.assettracker.models.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class JwtRequestDTO {

	private String email;
	private String password;
}
