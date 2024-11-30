package com.cognizant.assettracker.repositories;

import com.cognizant.assettracker.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Query("select u from User u where u.employeeId = ?1")
	User findByEmployeeIdEquals(String employeeId);

	@Query("select u.email from User u where u.employeeId = ?1")
	String findByEmailByEmployeeId(String employeeId);

	public Optional<User> findByEmail(String email);

	Optional<User> findByResetToken(String token);

	@Query(value = "select u.* from user_table u join user_roles ur on employee_id =ur.user_employee_id where ur.roles = 'NEW_USER';" ,nativeQuery = true)
	public List<User> findByRole();

	@Query(value = "select u.email from user_table u join user_roles ur on employee_id =ur.user_employee_id where ur.roles = 'PMO';" ,nativeQuery = true)
	public List<String> findByPMORole();


	boolean existsByEmployeeIdOrEmail(String employeeIdValue, String emailValue);
}
