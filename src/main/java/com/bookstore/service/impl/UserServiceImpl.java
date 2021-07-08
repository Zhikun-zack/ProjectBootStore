package com.bookstore.service.impl;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	
	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}
}
