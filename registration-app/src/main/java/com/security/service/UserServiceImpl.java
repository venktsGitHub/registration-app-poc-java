package com.security.service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entity.PasswordResetToken;
import com.security.entity.User;
import com.security.entity.VerificationToken;
import com.security.model.PasswordModel;
import com.security.model.UserModel;
import com.security.repo.PasswordResetTokenRepository;
import com.security.repo.UserRepo;
import com.security.repo.VerificationTokenRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo uRepo;
	
	@Autowired
	private PasswordEncoder pEncoder;
	
	@Autowired
	private VerificationTokenRepo vTokenRepo;
	
	@Autowired
	private PasswordResetTokenRepository pTokenRepository;
	
	@Override
	public User registerUser(UserModel uModel) {
		
		User user = new User();
		
		user.setEmail(uModel.getEmail());
		user.setFirstName(uModel.getFirstName());
		user.setLastName(uModel.getLastName());
		user.setRole("USER");
		user.setPassword(pEncoder.encode(uModel.getPassword()));
		
		uRepo.save(user);
		
		return user;
	}

	@Override
	public void saveVerificationTokenForUser(User user, String token) {
		
		VerificationToken vToken = new VerificationToken(token, user);
		
		vTokenRepo.save(vToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		
		VerificationToken verificationToken = vTokenRepo.findByToken(token);
		
		if(verificationToken==null) {
			return "InValid";
		}
		
		User user = verificationToken.getUser();
		
		Calendar cal = Calendar.getInstance();
		
		if(verificationToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0) {
			
			vTokenRepo.delete(verificationToken);
			
			return "expired";
			
		}
		
		user.setEnabled(true);
		
		uRepo.save(user);
		
		return "valid";
	}

	@Override
	public VerificationToken generateNewToken(String oldToken) {
		
		VerificationToken verificationToken = vTokenRepo.findByToken(oldToken);
		
		verificationToken.setToken(UUID.randomUUID().toString());
	
		vTokenRepo.save(verificationToken);
		
		
		return verificationToken;
		
	}

	@Override
	public User validateEmailIforResetPassword(String email) {
		// TODO Auto-generated method stub
		return uRepo.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		
		PasswordResetToken pResetToken = new PasswordResetToken(token,user);
		
		pTokenRepository.save(pResetToken);
		
	}

	@Override
	public boolean findUserByPasswordResetToken(String token) {

		PasswordResetToken passwordResetToken = pTokenRepository.findByToken(token);
		
		if(passwordResetToken==null) {
			return false;
		}
		
		
		
		Calendar cal = Calendar.getInstance();
		
		if(passwordResetToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0) {
			
			pTokenRepository.delete(passwordResetToken);
			
			return false;
			
		}
		
		
		return true;
	}

	@Override
	public Optional<User> getUserByPasswordResetToken(String token) {
		
		return Optional.ofNullable(pTokenRepository.findByToken(token).getUser());
	}

	@Override
	public void saveResetPassword(User user, PasswordModel pModel) {
		
		if(pModel.getPassword().equals(pModel.getConfirmPassword())) {
			user.setPassword(pEncoder.encode(pModel.getPassword()));
			uRepo.save(user);
		}
		
		//uRepo.save();
		
	}

	@Override
	public User findUserByEmailId(String email) {
		
		
		
		
		return uRepo.findByEmail(email);
	}

	@Override
	public boolean validateOldPassword(User user, String confirmPassword) {
		
		
		
		return pEncoder.matches(confirmPassword, user.getPassword());
	}

	@Override
	public void changePassword(User user, String confirmPassword) {
		user.setPassword(pEncoder.encode(confirmPassword));
		uRepo.save(user);
		
	}



}
