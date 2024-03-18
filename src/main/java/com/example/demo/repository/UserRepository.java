package com.example.demo.repository;



import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

@Repository 
@EnableJpaRepositories

public interface UserRepository extends JpaRepository<User, Long> {

	
	User findByEmail (String email);
    User findByFirstname(String firstname);
	
	User findByRole(String role);

	List<User> findAll();

	Optional<User> findOneByEmailAndPassword(String firstname, String password);
//	
//	 @Query("SELECT a.fullname FROM User a WHERE a.email = :email")
//	 String findUsernameByEmail(String email);
	 
	 @Modifying
	 @Transactional
	 @Query(value = "INSERT INTO email_otp (email, otp) VALUES (:email, :otp)", nativeQuery = true)
	 void saveEmailAndOTP(@Param("email") String email, @Param("otp") String otp);

	 

	
	
}
