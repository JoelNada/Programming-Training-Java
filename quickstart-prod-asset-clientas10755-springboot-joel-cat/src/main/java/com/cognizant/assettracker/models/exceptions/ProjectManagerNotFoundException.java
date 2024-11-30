package com.cognizant.assettracker.models.exceptions;

public class ProjectManagerNotFoundException extends RuntimeException {
	public ProjectManagerNotFoundException(Long id) {
		super("Associate Not found for " + id);
	}

}
