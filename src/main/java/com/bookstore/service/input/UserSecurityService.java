package com.bookstore.service.input;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.Repository.userRepository;

@Service
public class UserSecurityService implements UserDetailsService {
	@Autowired
	private userRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername (String username ) throws UsernameNotFoundException{
		User user = userRepository.findByUsername(username);
		
		if(null == user) {
			throw new UsernameNotFoundException("Username not found");
		}
		return user;
	}
}
