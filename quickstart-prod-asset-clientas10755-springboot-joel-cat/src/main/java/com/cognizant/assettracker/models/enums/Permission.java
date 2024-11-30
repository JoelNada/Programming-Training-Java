package com.cognizant.assettracker.models.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

	PMO_READ("pmo:read"),
	PMO_CREATE("pmo:create"),
	PMO_UPDATE("pmo:update"),
	PMO_DELETE("pmo:delete"),

	EPL_READ("epl:read"),
	EPL_CREATE("epl:create"),
	EPL_UPDATE("epl:update"),
	EPL_DELETE("epl:delete"),

	ESA_PM_READ("esa_pm:read"),
	ESA_PM_CREATE("esa_pm:create"),
	ESA_PM_UPDATE("esa_pm:update"),
	ESA_PM_DELETE("esa_pm:delete");

	@Getter
	private final String permission;
}

