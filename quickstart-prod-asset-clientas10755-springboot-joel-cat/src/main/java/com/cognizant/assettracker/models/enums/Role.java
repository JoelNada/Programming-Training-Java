package com.cognizant.assettracker.models.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cognizant.assettracker.models.enums.Permission.*;
import static com.cognizant.assettracker.models.enums.Permission.EPL_DELETE;

@RequiredArgsConstructor
public enum Role {
	PMO(
			Set.of(
					PMO_CREATE,
					PMO_READ,
					PMO_UPDATE,
					PMO_DELETE,
					EPL_CREATE,
					EPL_READ,
					EPL_UPDATE,
					EPL_DELETE,
					ESA_PM_CREATE,
					ESA_PM_READ,
					ESA_PM_UPDATE,
					ESA_PM_DELETE
			)
	),
	ESA_PM(
			Set.of(
					ESA_PM_CREATE,
					ESA_PM_READ,
					ESA_PM_UPDATE,
					ESA_PM_DELETE
			)
	),
	EPL(
			Set.of(EPL_CREATE,
					EPL_READ,
					EPL_UPDATE,
					EPL_DELETE)
	),
	NEW_USER(Collections.emptySet());

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities(){
		var authorities =getPermissions()
				.stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return authorities;
	}
}

