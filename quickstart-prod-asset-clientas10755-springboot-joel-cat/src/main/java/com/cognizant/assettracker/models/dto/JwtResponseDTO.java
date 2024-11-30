package com.cognizant.assettracker.models.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {
	private  String jwtToken;
	private String username;
	private String[] roles;
}
