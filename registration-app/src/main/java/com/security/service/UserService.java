package com.security.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.security.entity.User;
import com.security.entity.VerificationToken;
import com.security.model.PasswordModel;
import com.security.model.UserModel;


@Service
public interface UserService {

	User registerUser(UserModel uModel);

	void saveVerificationTokenForUser(User user, String token);

	String validateVerificationToken(String token);

	VerificationToken generateNewToken(String oldToken);

	User validateEmailIforResetPassword(String email);

	void createPasswordResetTokenForUser(User user, String token);

	boolean findUserByPasswordResetToken(String token);

	Optional<User> getUserByPasswordResetToken(String token);

	void saveResetPassword(User user, PasswordModel pModel);

	User findUserByEmailId(String email);

	boolean validateOldPassword(User user, String confirmPassword);

	void changePassword(User user, String confirmPassword);

}
