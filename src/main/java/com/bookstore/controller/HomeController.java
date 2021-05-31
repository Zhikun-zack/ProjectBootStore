package com.bookstore.controller;


import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.UserSecurityService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	 
	@RequestMapping("/myAccount")
	public String myAccount() {
		return "myAccount";
	}
	
	@RequestMapping("/userLogin")
	//Model add a map and storing a pair of value into it 
	public String userLogin (Model model) {
		//For HTML view
		model.addAttribute("classActiveLgoin", true);
		return "myAccount";
	}
	@RequestMapping("/forgetPassword")
	public String forgetPassword (Model model) {
		model.addAttribute("classActiveForgetPassword", true);
		return "myAccount";
	}
	@RequestMapping("/newUser")
	public String newUser(
			Locale locale,
			@RequestParam("token" ) String token,
			Model model) {
		//get token
		PasswordResetToken passToken = userService.getPasswordResetToken(token); 
		//if user has not signed in
		if (passToken == null) {
			String message = "Invalid Token." ;
			//Translate error message to View using model
			model.addAttribute("message", message);
			//An default URL for bad request
			return "redirect:/badRequest";
		}
		//If exist user, get user class and name
		User user = passToken.getUser();
		String username = user.getUsername();
		
		//Set account to that log in user
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, 
				userDetails.getPassword(), 
				userDetails.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}

}