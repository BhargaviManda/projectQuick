package com.example.demo.service;



import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ServicerDto;

import com.example.demo.entity.Servicer;
import com.example.demo.entity.User;
import com.example.demo.repository.ServicerRepository;
import com.example.demo.response.LoginResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class ServicerService {

	@Autowired
    private PasswordEncoder passwordEncoder;
	

	@Autowired
    private ServicerRepository servicerRepo;
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	public String addServicer(ServicerDto servicerDTO) {
   	 Servicer servicer = servicerRepo.findByEmail(servicerDTO.getEmail());
        if (servicer == null) {
       	 servicer = new Servicer();  // Add this line to create a new User object
        }
               
       
   	
       //User user = new User();
       servicer.setFirstname(servicerDTO.getFirstname());
       servicer.setAadharnumber(servicerDTO.getAadharnumber());
       servicer.setService(servicerDTO.getService());
       servicer.setEmail(servicerDTO.getEmail());
       servicer.setPassword(passwordEncoder.encode(servicerDTO.getPassword()));
       servicer.setConfirmpassword(passwordEncoder.encode(servicerDTO.getConfirmpassword()));
       servicer.setPhonenumber(servicerDTO.getPhonenumber());
       servicer.setAddress(servicerDTO.getAddress());
       servicer.setPincode(servicerDTO.getPincode());
       servicer.setRole(servicerDTO.getRole());
       servicerRepo.save(servicer);
       return servicer.getFirstname();
   }
	
	public int resetPassword(Servicer servicer) {
	    Servicer servicerFromRepo = servicerRepo.findByEmail(servicer.getEmail());
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    
	    if (servicerFromRepo == null) {
	        return 1; // User is not registered
	    }
	    
	    if (servicerFromRepo.getOtp().equals(servicer.getOtp())) {
	        String password = encoder.encode(servicer.getPassword());
	        servicerFromRepo.setPassword(password);
	        servicerRepo.save(servicerFromRepo);
	        return 2; // Password reset successful
	    }
	    
	    return 3; // OTP is not matched
	}
	public void sendOtpService(String email) {
 		String otp=generateOtp();
 		if (otp == null) {
             throw new RuntimeException("Failed to generate OTP");
         }
 		Servicer servicer = servicerRepo.findByEmail(email);
 		if (servicer==null){
 			 servicer=new Servicer();
 		}
 		servicer.setEmail(email);
 		//System.out.println(user.getEmail());
 		servicer.setOtp(otp);
 		servicerRepo.save(servicer);
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
 	


	

//    public boolean loginServicer(String email, String password) {
//        Servicer servicer = servicerRepo.findByEmail(email);
//        if (servicer != null && servicer.getPassword().equals(password)) {
//            return true; // Authentication successful
//        }
//        return false; // Authentication failed
//    }
	
//
//	 public LoginResponse loginServicer(LoginDTO loginDTO) {
//		 Servicer servicer1 = servicerRepo.findByEmail(loginDTO.getEmail());
//	        if (servicer1 != null && servicer1.getRole().equals("service-provider")) {
//	            String password = loginDTO.getPassword();
//	            String encodedPassword = servicer1.getPassword();
//	            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//	            if (isPwdRight) {
//	                Optional<Servicer> servicer = servicerRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
//	                if (servicer.isPresent()) {
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
	
}
