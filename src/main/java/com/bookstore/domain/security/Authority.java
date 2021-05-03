package com.bookstore.domain.security;

import org.springframework.security.core.GrantedAuthority;
//This class is for security
public class Authority implements GrantedAuthority{
	private final String authority;
	
	public Authority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}
}
