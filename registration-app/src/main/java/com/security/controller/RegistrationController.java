package com.security.controller;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.User;
import com.security.entity.VerificationToken;
import com.security.event.RegistrationConfirmationEvent;
import com.security.model.PasswordModel;
import com.security.model.UserModel;
import com.security.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegistrationController {
	
	
	@Autowired
	private UserService uService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping("/register")
	public String register(@RequestBody UserModel uModel, final HttpServletRequest request) {
		
		User user = uService.registerUser(uModel);
		
		publisher.publishEvent(new RegistrationConfirmationEvent(user, applicationUrl(request)));
		
		return "User Successfully Created!";
		
	}

	@GetMapping("/verifyToken")
	public String verifyRegistration(@RequestParam("token") String token) {
		
		String result = uService.validateVerificationToken(token);
		
		if(result.equalsIgnoreCase("valid")) {
			return "user Verified Successfully";
		}
		
		return "Bad User";
	}
	
	
	@GetMapping("/resendToken")
	public String resendToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		
		VerificationToken vToken = uService.generateNewToken(oldToken);
		
		User user = vToken.getUser();
		
		resendVerificationMail(user,applicationUrl(request) ,vToken.getToken());
		
		
		
		return "Verification Lik has been sent!";
	}
	
	
	//reset password and change password
	
	@PostMapping("/resetPassword")
	public String passwordReset(@RequestBody PasswordModel pModel,HttpServletRequest request){
		
		
		User user = uService.validateEmailIforResetPassword(pModel.getEmail());
		String url="";
		
		if(user==null) {
			return "Invalid emailId or User is not Registered with us!";
		}
		
		else {
			
			String token= UUID.randomUUID().toString();
			uService.createPasswordResetTokenForUser(user, token);
			url = passwordResetMail(user,applicationUrl(request),token);
			
			
		}
		
	
		return null;
		
	}
	
	@PostMapping("/savePassword")
	public String savePassword(@RequestBody PasswordModel pModel, @RequestParam String token) {
		
		boolean isValid = uService.findUserByPasswordResetToken(token);
		
		if(isValid) {
			
			Optional<User> user = uService.getUserByPasswordResetToken(token);
			
			uService.saveResetPassword(user.get(), pModel);
			
			return "password reset completed Successfully";
			
		}
		
		
		
		
		return "Invalid Token";
		
	}
	
	
	@PostMapping("/changePassword")
	public String ChangePassword(@RequestBody PasswordModel pModel) {
		
		
		User user = uService.findUserByEmailId(pModel.getEmail());
		
		if(user!=null) {
			
			log.info("inside 1");
		
			if(uService.validateOldPassword(user, pModel.getPassword())) {
				
				
				log.info("inside 2");
			
				uService.changePassword(user,pModel.getConfirmPassword());
				return "password changed Successfully!";
			}
		
		}
		
		
		return "Bad request";
		
	}
	
private String passwordResetMail(User user, String applicationUrl, String token) {
		
		String url =  
				applicationUrl
				+"/savePassword?token="
				+token;
		// Create code for sending email here
		log.info("Click on this url to reset password: {} ",url);
		return url;
		
	}
	
	
	private void resendVerificationMail(User user, String applicationUrl, String token) {
		
		
		
		
		
		
		String url =  
				applicationUrl
				+"/verifyToken?token="
				+token;
		// Create code for sending email here
		log.info("Click on this url to confirm user: {} ",url);
		
	}

	private String applicationUrl(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return "http://"+request.getServerName()+":"+
				request.getServerPort()+request.getContextPath();
	}

}
