package com.bookstore.domain.security;

import org.springframework.security.core.GrantedAuthority;
//This class is for security
public class Authority implements GrantedAuthority{
	//the users' authority, what they can do, stored in string type
	private final String authority;
	
	//Constructor
	public Authority(String authority) {
		this.authority = authority;
	}
	
	//get the authority of this class
	@Override
	public String getAuthority() {
		return authority;
	}
}
