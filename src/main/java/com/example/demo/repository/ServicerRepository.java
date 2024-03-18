package com.example.demo.repository;


import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Servicer;

import jakarta.transaction.Transactional;


@Repository
@EnableJpaRepositories
public interface ServicerRepository extends JpaRepository<Servicer, Long> {
	Servicer findByEmail (String email);
	
	
    Servicer findByFirstname(String firstname);
	
	Servicer findByRole(String role);

	List<Servicer> findAll();

	Optional<Servicer> findOneByEmailAndPassword(String firstname, String password);
	
	@Modifying
	 @Transactional
	 @Query(value = "INSERT INTO email_otp (email, otp) VALUES (:email, :otp)", nativeQuery = true)
	 void saveEmailAndOTP(@Param("email") String email, @Param("otp") String otp);
}
