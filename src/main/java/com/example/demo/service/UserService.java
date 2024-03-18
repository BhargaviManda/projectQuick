package com.example.demo.service;



import java.security.SecureRandom;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.response.LoginResponse;




@Service
public class UserService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	
	 public String addUser(UserDto userDTO) {
    	 User user = userRepo.findByEmail(userDTO.getEmail());
         if (user == null) {
        	 user = new User();  // Add this line to create a new User object
         }
         
        
    	
        //User user = new User();
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setConfirmpassword(passwordEncoder.encode(userDTO.getConfirmpassword()));
        user.setPhonenumber(userDTO.getPhonenumber());
        user.setAddress(userDTO.getAddress());
        user.setPincode(userDTO.getPincode());
        user.setRole(userDTO.getRole());
        userRepo.save(user);
        return user.getFirstname();
    }
	 
     public void sendOtpService(String email) {
 		String otp=generateOtp();
 		if (otp == null) {
             throw new RuntimeException("Failed to generate OTP");
         }
 		User user = userRepo.findByEmail(email);
 		if (user==null){
 			 user=new User();
 		}
 		user.setEmail(email);
 		//System.out.println(user.getEmail());
 		user.setOtp(otp);
 		userRepo.save(user);
 		try {
 			sendOtpToMail(email,otp);
 			
 		}catch(MessagingException e) {
 			throw new RuntimeException("unable to send otp");
 		}
 		
 	}
 	public String generateOtp() {
 		SecureRandom random=new SecureRandom();
 		int otp= 100000 + random.nextInt(900000);
 		return String.valueOf(otp);
 	}
 	
 	private void sendOtpToMail(String email,String otp) throws MessagingException {
 		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
 		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
 		mimeMessageHelper.setTo(email);
 		mimeMessageHelper.setSubject("Your OTP is:");
 		mimeMessageHelper.setText(otp);
 		javaMailSender.send(mimeMessage);
 				
 	}


// 	public boolean verifyOtp(String email, String otp) {
// 		 User user = userRepo.findByEmail(email);
// 		 if (user.getOtp() .equals(otp))
// 		 {
// 			 return true;
// 		 }
// 		return false;
// 	}

 	
 	public int resetPassword(User user) {
 		User user1 = userRepo.findByEmail(user.getEmail());
 		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
 		if (user1==null) {
 			return 1;
 		}
 		 if (user1.getOtp() .equals(user.getOtp())){
 			 String password=encoder.encode(user.getPassword());
 			 user1.setPassword(password);
 			 userRepo.save(user1);
 			 return 2;
 		 }
 		 return 3;
 		 
 	}
 

//	 public boolean loginUser(String email, String password) {
//	        User user = userRepo.findByEmail(email);
//	        if (user != null && user.getPassword().equals(password)) {
//	            return true; // Authentication successful
//	        }
//	        return false; // Authentication failed
//	    }
//    
//	 public LoginResponse loginUser(LoginDTO loginDTO) {
//	        User user1 = userRepo.findByEmail(loginDTO.getEmail());
//	        if (user1 != null && user1.getRole().equals("user")) {
//	            String password = loginDTO.getPassword();
//	            String encodedPassword = user1.getPassword();
//	            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//	            if (isPwdRight) {  
//	                Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
//	                if (user.isPresent()) {
//	                    return new LoginResponse("Login Success", true); // Fixed syntax
//	                } else {
//	                    return new LoginResponse("Login Failed", false);
//	                }
//	            } else {
//	                return new LoginResponse("Password Not Match", false); // Fixed typo
//	            }
//	        } else {
//	            return new LoginResponse("Email not exists", false);
//	        }
//	        
//	    }
//	 public LoginResponse loginAdmin(User user) {
//			User user2 = userRepo.findByEmail(user.getEmail());
//	        if (user2 != null && user2.getRole().equals("admin")) {
//	            String password = user.getPassword();
//	            String encodedPassword = user2.getPassword();
////	            System.out.println(password);
//	            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//	            if (isPwdRight) {
//	                Optional<User> adm = userRepo.findOneByEmailAndPassword(user.getEmail(), encodedPassword);
//	                if (adm.isPresent()){
//	                    return new LoginResponse("Login Success", true); // Fixed syntax
//	                } else {
//	                    return new LoginResponse("Login Failed", false);
//	                }
//	            } else {
//	                return new LoginResponse("Password Not Match", false); // Fixed typo
//	            }
//	        } else {
//	            return new LoginResponse("Email not exists", false);
//	        }
//		}
	 
	 
}

