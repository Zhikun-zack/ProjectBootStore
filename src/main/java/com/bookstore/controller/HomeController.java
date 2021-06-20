package com.bookstore.controller;


import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

//The servlet container creates an HttpServletRequest object and passes it as an argument to the servlet's service methods (doGet, doPost, etc).
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
//Primarily designed for adding attributes to the model. And interaction between java and html frontend
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.UserSecurityService;
import com.bookstore.utility.SecurityUtility;

@Controller
public class HomeController {
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailConctructor mailConstructor;
	
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
	public String forgetPassword (
			HttpServletRequest request,
			//front end html will get the email attribute
			@ModelAttribute("email") String email
			Model model
			) {
		model.addAttribute("classActiveForgetPassword", true);
		
		User user = userService.findByEmail(email);
		if(user == null) {
			model.addAttribute("emailNotExist", true);
			return "myAccount";
		}
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password)	;
		user.setPassword(encryptedPassword);
		
		userService.createUser(user);
		
		String token = UUID.randomUUID().toSTring;
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath();
		
		SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("forgetPasswordEmailSent", "true");
		
		return "myAccount";
	}
	//The primary mapping expressed by this annotation.
	//method: The HTTP request methods to map to, narrowing the primary mapping
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			//front end html will get the email attribute
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception{
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
		
		//user name exist
		if(userService.findByUsername(username) != null) {
			model.addAttribute("usernameExists", true);
			return "myAccount";
		}
		//user email exist
		if(userService.findByEmail(userEmail) != null) {
			model.addAttribute("emailExists", true);
			return "myAccount";
		}
		//store user information
		User user = new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password = SecurityUtility.randomPassword();
		
		String encryptedPassword = SecurityUtility.passwordEncoder().encode(password)	;
		user.setPassword(encryptedPassword);
		
		Role role = new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(new UserRole(user, role));
		userService.createUser(user,userRoles);
		
		String token = UUID.randomUUID().toSTring;
		userService.createPasswordResetTokenForUser(user, token);
		
		String appUrl = "http://" + request.getServerName()+ ":" + request.getServerPort() + request.getContextPath();
		
		SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
		
		mailSender.send(email);
		
		model.addAttribute("emailSent", "true");
		
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
		
		model.addAttribute("user", user);
		model.addAttribute("classActiveEdit", true);
		
		return "myProfile";
	}

}