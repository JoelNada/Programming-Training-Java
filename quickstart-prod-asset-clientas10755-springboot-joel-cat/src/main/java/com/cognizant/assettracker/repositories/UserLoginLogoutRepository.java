package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.UserLoginLogout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoginLogoutRepository extends JpaRepository <UserLoginLogout, Long> {
	Optional<UserLoginLogout> findFirstByUsernameOrderByLoginTimeDesc(String username);
}
