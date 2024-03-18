package com.example.demo.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Servicer;
import com.example.demo.repository.ServicerRepository;



@Controller
@RequestMapping("/service-provider")
public class ProviderController {

	@Autowired
	private ServicerRepository servicerRepo;

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
	
	@GetMapping("/profile")
	public String profile() {
		return "/Servicer/servicedash";
	}

	

}