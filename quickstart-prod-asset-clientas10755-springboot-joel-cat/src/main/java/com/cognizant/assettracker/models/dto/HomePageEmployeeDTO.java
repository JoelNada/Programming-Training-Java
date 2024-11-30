package com.cognizant.assettracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageEmployeeDTO {
	private Long associateId;
	private String associateName;
}
