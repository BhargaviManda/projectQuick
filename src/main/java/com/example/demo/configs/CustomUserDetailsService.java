package com.example.demo.configs;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Servicer;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ServicerRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired 
	private UserRepository userRepo;
	
 @Autowired 
	    private ServicerRepository servicerRepository;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		 User user = userRepo.findByEmail(username);
        if (user != null) {
	            return new CustomUser(user);
	        }

	        Servicer servicer = servicerRepository.findByEmail(username);
	        if (servicer != null) {
	            return new CustomUser(servicer);
	        }

	        throw new UsernameNotFoundException("User not found with email: " + username);
    }
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		User user = userRepo.findByEmail(username);
//		System.out.println(user);
//		if (user == null) {
//			throw new UsernameNotFoundException("user not found");
//		} else {
//			return new CustomUser(user);
//		}
//
//	}
	

	

}
