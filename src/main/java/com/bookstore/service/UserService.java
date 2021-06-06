package com.bookstore.service;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;

//all the possible functions that can be used for user
//Just a function name, the implementation is in UserServiceImpl.java
public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
}
