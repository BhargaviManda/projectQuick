package com.example.demo.entity;



import jakarta.persistence.Transient; // Import annotation for marking transient fields
import jakarta.validation.constraints.Email; // Import annotation for email validation
import jakarta.validation.constraints.NotBlank; // Import annotation for checking blank values

public interface ResettableEntity {
    
    @NotBlank(message = "Email is required") // Annotation to ensure email is not blank
    @Email(message = "Invalid email format") // Annotation to validate email format
    String getEmail(); // Define method to get email address
    
    String getOtp(); // Define method to get OTP
    
    void setOtp(String otp); // Define method to set OTP
    
    @NotBlank(message = "Password is required") // Annotation to ensure password is not blank
    String getPassword(); // Define method to get password
    
    void setPassword(String password); // Define method to set password
}

