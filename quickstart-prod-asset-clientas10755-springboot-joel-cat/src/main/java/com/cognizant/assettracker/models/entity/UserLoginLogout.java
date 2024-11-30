package com.cognizant.assettracker.models.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user_login_logout")
public class UserLoginLogout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(name = "login_time", nullable = false)
	private LocalDateTime loginTime;

	@Column(name = "logout_time")
	private LocalDateTime logoutTime;

	@Column(name = "session_duration")
	private Duration sessionDuration;

}

