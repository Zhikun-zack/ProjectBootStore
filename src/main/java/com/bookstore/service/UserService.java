package com.bookstore.service;

import org.apache.catalina.User;

import com.bookstore.domain.security.PasswordResetToken;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
}
