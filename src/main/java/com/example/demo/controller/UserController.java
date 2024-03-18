package com.example.demo.controller;

import java.security.Principal;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.demo.dto.ServicerDto;
import com.example.demo.dto.UserDto;


import com.example.demo.service.ServicerService;
import com.example.demo.service.UserService;
import com.example.demo.entity.ResettableEntity;
import com.example.demo.entity.Servicer;
import com.example.demo.entity.User;
import com.example.demo.repository.ServicerRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;







@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ServicerRepository servicerRepo;
	

	@Autowired
	private ServicerService servicerService;
	

    
	@GetMapping("/register")
	public String Home(@ModelAttribute("user") UserDto userDto) {
		return "registration";
	}
	
	@GetMapping("/serviceregistration")
	public String Home(@ModelAttribute("service") ServicerDto servicerDto) {
		return "serv_reg";
	}
	
	@PostMapping(path = "/register")
	public String saveUser(@ModelAttribute("user") UserDto userDto,HttpSession session,Model model) {
		userDto.setRole("ROLE_USER");
		userDto.setRole(userDto.getRole());
		userService.addUser(userDto);
		model.addAttribute("message", "Registered");
		return "redirect:/login1";
	}
	
	@PostMapping(path = "/serviceregistration")
	public String saveUser(@ModelAttribute("service") ServicerDto servicerDto,HttpSession session,Model model) {
		servicerDto.setRole("ROLE_SERVICE-PROVIDER");
		servicerDto.setRole(servicerDto.getRole());
		servicerService.addServicer(servicerDto);
		model.addAttribute("message", "Registered");
		return "redirect:/login1";
	}
//	
	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}
	
	@ModelAttribute
    public void commonServicer(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            Servicer servicer = servicerRepo.findByEmail(email);
            if (servicer != null) {
                model.addAttribute("servicer", servicer);
            }
        }
    }
	
//	@PostMapping("/resetPassword")
//	public String resetPassword(@RequestParam("email") String email, Model model) {
//	    int status = userService.resetPassword(email);
//
//	    if (status == 1) {
//	        model.addAttribute("message", "User is not registered");
//	        return "forgetPassword";
//	    } else if (status == 2) {
//	        // Password reset successfully for User
//	        return "login";
//	    } else if (status == 3) {
//	        model.addAttribute("message", "Otp is not matched for User");
//	        return "forgetPassword";
//	    } else if (status == 4) {
//	        // Password reset successfully for Servicer
//	        return "login";
//	    } else if (status == 5) {
//	        model.addAttribute("message", "Otp is not matched for Servicer");
//	        return "forgetPassword";
//	    } else {
//	        model.addAttribute("message", "Email not found");
//	        return "forgetPassword";
//	    }
//	}

	
//	@PostMapping("/resetPassword")
//    public String resetPassword(@ModelAttribute("detail") User user, Model model) {
//        System.out.println("reset password");
//        int status = userService.resetPassword(user);
//        
//        if (status == 1) {
//            model.addAttribute("message", "User is not registered");
//            return "forgetPassword";
//        } else if (status == 3) {
//            model.addAttribute("message", "Otp is not matched");
//            return "forgetPassword";
//        }
//        return "login";
//    }
//	@PostMapping("/resetPassword")
//	public String resetPassword(@ModelAttribute("detail") ResettableEntity entity, Model model) {
//	    System.out.println("reset password");
//
//	    int status;
//	    
//	    if (entity instanceof User) {
//	        status = userService.resetPassword((User) entity);
//	    } else if (entity instanceof Servicer) {
//	        status = servicerService.resetPassword((Servicer) entity);
//	    } else {
//	        // Handle unsupported entity type
//	        return "error"; // Or handle as per your application's logic
//	    }
//	    
//	    if (status == 1) {
//	        model.addAttribute("message", entity.getClass().getSimpleName() + " is not registered");
//	        return "forgetPassword";
//	    } else if (status == 3) {
//	        model.addAttribute("message", "OTP is not matched");
//	        return "forgetPassword";
//	    }
//	    return "login";
//	}

	@PostMapping("/resetPassword")
	public String resetPassword(@ModelAttribute("detail") ResettableEntity entity, Model model) {
	    System.out.println("reset password");

	    int status;
	    
	    if (entity instanceof User) {
	        status = userService.resetPassword((User) entity);
	    } else if (entity instanceof Servicer) {
	        status = servicerService.resetPassword((Servicer) entity);
	    } else {
	        // Handle unsupported entity type
	        return "error"; // Or handle as per your application's logic
	    }
	    
	    if (status == 1) {
	        model.addAttribute("message", entity.getClass().getSimpleName() + " is not registered");
	        return "forgetPassword";
	    } else if (status == 3) {
	        model.addAttribute("message", "OTP is not matched");
	        return "forgetPassword";
	    }
	    
	    // Add an alert message for profile update
	    model.addAttribute("message", "Profile updated successfully");
	    
	    // Redirect to the login page
	    return "redirect:/login1";
	}

	
//	@PostMapping("/sendOtp/{email}")
//	public String sendOtpToMail(@PathVariable("email") String email, @ModelAttribute("type") String type) {
//	    if("user".equalsIgnoreCase(type)){
//	        userService.sendOtpService(email);
//	    } else if("servicer".equalsIgnoreCase(type)){
//	        servicerService.sendOtpService(email);
//	    } else {
//	        return "Invalid type provided";
//	    }
//	    return "otp send successfully";
//	}

//	@PostMapping("/sendOtp/{email}/{entityType}")
//	public String sendOtpToMail(@PathVariable("email") String email, @PathVariable("entityType") String entityType) {
//	    if("user".equalsIgnoreCase(entityType)) {
//	        userService.sendOtpService(email);
//	    } else if("servicer".equalsIgnoreCase(entityType)) {
//	        servicerService.sendOtpService(email);
//	    } else {
//	        return "Invalid type provided";
//	    }
//	    return "otp send successfully";
//
//	}
	
//	@PostMapping("/sendOtp/{email}/{entityType}")
//	public ResponseEntity<String> sendOtpToMail(@PathVariable("email") String email, @PathVariable("entityType") String entityType) {
//	    try {
//	        if("user".equalsIgnoreCase(entityType)) {
//	            userService.sendOtpService(email);
//	        } else if("servicer".equalsIgnoreCase(entityType)) {
//	            servicerService.sendOtpService(email);
//	        } else {
//	            return ResponseEntity.badRequest().body("Invalid type provided");
//	        }
//	        return ResponseEntity.ok("OTP sent successfully");
//	    } catch (Exception e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP: " + e.getMessage());
//	    }
//	}
	@PostMapping("/sendOtp/{email}/{entityType}")
	public ResponseEntity<String> sendOtpToMail(@PathVariable("email") String email, @PathVariable("entityType") String entityType) {
	    if("user".equalsIgnoreCase(entityType)) {
	        userService.sendOtpService(email);
	    } else if("servicer".equalsIgnoreCase(entityType)) {
	        servicerService.sendOtpService(email);
	    } else {
	        return ResponseEntity.badRequest().body("Invalid type provided");
	    }
	    return ResponseEntity.ok("OTP sent successfully");
	}

	
	@GetMapping("/checkEmailExists/{entityType}/{email}")
	public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String entityType, @PathVariable String email) {
	    boolean exists = false;
	    
	    switch (entityType.toLowerCase()) {
	        case "user":
	            exists = userRepo.findByEmail(email) != null; // Check if user with given email exists
	            break;
	        case "servicer":
	            exists = servicerRepo.findByEmail(email) != null; // Check if servicer with given email exists
	            break;
	        default:
	            Map<String, Boolean> errorResponse = new HashMap<>();
	            errorResponse.put("error", false); // Indicate that there was an error
	            return ResponseEntity.badRequest().body(errorResponse);
	    }
	    
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("exists", exists);
	    return ResponseEntity.ok(response);
	}

	
//
//	@PostMapping("/sendOtp/{email}")
//	public String sendOtpToMail(@PathVariable("email") String email) {
//		userService.sendOtpService(email);
//		return "otp send successfully";
//	}
	
	
	
//	
//	 @GetMapping("/checkEmailExists/{email}")
//	 public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
//	        User user = userRepo.findByEmail(email);
//	        Map<String, Boolean> response = new HashMap<>();
//	        response.put("exists", user != null);
//	        return ResponseEntity.ok(response);
//	 }
//	 
//	@GetMapping("/checkEmailExists/{email}")
//	public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
//	    boolean userExists = userRepo.findByEmail(email) != null;
//	    boolean serviceExists = servicerRepo.findByEmail(email) != null;
//	    
//	    Map<String, Boolean> response = new HashMap<>();
//	    response.put("userExists", userExists);
//	    response.put("serviceExists", serviceExists);
//	    
//	    return ResponseEntity.ok(response);
//	}
	 
	 

	
	
//	@PostMapping(path = "/login")
//	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
//		//System.out.println("Working");
//		
//		//session.setAttribute("userEmail", loginDTO.getEmail());
//		LoginResponse loginResponse = userService.loginUser(loginDTO);
//		return ResponseEntity.ok(loginResponse);
//	}
//	
//	@PostMapping("/authenticate")
//    public ResponseEntity<?> loginAdmin(@RequestBody User user) {
//    	//session.setAttribute("adminEmail", user.getEmail());
//    	LoginResponse loginResponse = userService.loginAdmin(user);
//		return ResponseEntity.ok(loginResponse);
//    }
//	@PostMapping(path = "/servicer")
//	public ResponseEntity<?> loginServicer(@RequestBody LoginDTO loginDTO) {
//		//System.out.println("Working");
//		
//		//session.setAttribute("userEmail", loginDTO.getEmail());
//		LoginResponse loginResponse = servicerService.loginServicer(loginDTO);
//		return ResponseEntity.ok(loginResponse);
//	}
//
//    @PostMapping("/login")  
//    public String login(@RequestParam("role") String role,
//                        @RequestParam("email") String email,
//                        @RequestParam("password") String password,
//                        HttpSession session) {
//        if ("user".equals(role)) {
//            boolean loginSuccess = userService.loginUser(email,password);
//            if (loginSuccess) {
//                return "redirect:/userdash1";
//            } else {
//                return "redirect:/login?error1";
//            }
//        } 
//        else if ("admin".equals(role)) {
//            boolean loginSuccess = userService.loginUser(email,password);
//            if (loginSuccess) {
//                return "redirect:/admindash1";
//            } else {
//                return "redirect:/login?error2";
//            }
//        } 
//        else if ("service-provider".equals(role)) {
//            boolean loginSuccess = servicerService.loginServicer(email,password);
//            if (loginSuccess) {
//                return "redirect:/servicedash1";
//            } else {
//                return "redirect:/login?error";
//            }
//        }
//        else {
//            return "redirect:/login?error3";
//        }
//    }
	
}
