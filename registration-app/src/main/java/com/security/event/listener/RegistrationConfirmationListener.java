package com.security.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.security.entity.User;
import com.security.event.RegistrationConfirmationEvent;
import com.security.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class RegistrationConfirmationListener
implements ApplicationListener<RegistrationConfirmationEvent>{

	@Autowired
	private UserService service;
	
	@Override
	public void onApplicationEvent(RegistrationConfirmationEvent event) {
		// TODO Auto-generated method stub
		
		User user = event.getUser();
		
		String token = UUID.randomUUID().toString();
		
		service.saveVerificationTokenForUser(user, token);
		
		String url =  
				event.getApplicationUrl()
				+"/verifyToken?token="
				+token;
		//Create code for sending email here
		log.info("Click on this url to confirm user: {} ",url);
		
	}

}
