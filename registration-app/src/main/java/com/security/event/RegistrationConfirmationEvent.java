package com.security.event;

import org.springframework.context.ApplicationEvent;

import com.security.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class RegistrationConfirmationEvent extends ApplicationEvent {

	private User user;
	private String applicationUrl;
	
	
	public RegistrationConfirmationEvent(User user, String applicationUrl) {
		
		
		super(user);
		this.applicationUrl=applicationUrl;
		this.user=user;
		
	}

}
