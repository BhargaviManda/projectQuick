package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import com.example.demo.dto.UserDto;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String Home() {
		return "index";
	}
	
	
	
	@GetMapping("/login1")
	public String Login() {
		return "login";
	}
	
	@GetMapping("/homeclean")
	public String Login1() {
		return "homecleaning";
	}
	
	@GetMapping("/plumb")
	public String Plumb() {
		return "plumbing";
	}
	
	@GetMapping("/car")
	public String Cart() {
		return "cart";
	}
	@GetMapping("/car1")
	public String Cart1() {
		return "addproduct";
	}
	
	@GetMapping("/forgetPass")
	public String forgetPassword() {
        return "forgetPassword";
    }
	
	
	@GetMapping("/about1")
	public String About() {
		return "about";
	}
}
