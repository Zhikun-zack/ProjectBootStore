package com.bookstore.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.security.PasswordResetToken;

import jdk.vm.ci.meta.Local;

@Controller
public class HomeController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		//if user go to this path, then change the class of login div in myAccount.html file 
		model.addAttribute("classActiveLogin", true);
		return "myAccount";
	}
	
	@RequestMapping("/forgetPassword")
	public String forgetPassword(
			Locale locale,
			@RequestParam("token")  String token,
			Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);
		
		model.addAttribute("classActiveForgetPassword", true);
		return "myAccount";
	}
	
	@RequestMapping("/newUser")
	public String newUser(Model model) {
		//if user go to this path, then change the class of login div in myAccount.html file 
		model.addAttribute("classActiveNewUser", true);
		return "myAccount";
	}
	
	
}