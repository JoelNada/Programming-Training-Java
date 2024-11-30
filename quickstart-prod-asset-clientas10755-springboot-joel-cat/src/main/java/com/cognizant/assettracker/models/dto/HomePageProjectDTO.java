package com.cognizant.assettracker.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomePageProjectDTO {
	private long projectId;
	private String projectName;

}
