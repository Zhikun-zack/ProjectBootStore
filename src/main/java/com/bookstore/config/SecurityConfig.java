package com.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bookstore.service.impl.UserSecurityService;
import com.bookstore.utility.SecurityUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	//do not need to log in when visit those path
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/",
			"/myAccount",
			"/newUser",
			"/forgetPassword",
			"/login",
			"/fonts/**",
			"/bookshelf",
			"/bookDetail/**",
			"/hours",
			"/faq",
			"/searchByCategory",
			"/searchBook"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS)
			.permitAll()
			.anyRequest()
			.authenticated();
		
		http
			.csrf()
			.disable().cors().disable()
//			if fail, redirect to "/login?error" with error as param, then we can use it as a signal for myAccount show some error div window
			.formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
			.loginPage("/login").permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
		}
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
