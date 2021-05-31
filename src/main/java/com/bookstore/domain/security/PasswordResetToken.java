package com.bookstore.domain.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.bookstore.domain.User;

//Linked with user, Mainly for calculate the expiration time and after that update the password token
@Entity
/*
 * Four attributes: id, token, user and expiryDate
 * id: primary key, automatically generated
 * token: string
 * user: User type, User type is another class created external
 * expiryDate: Date type
 */
public class PasswordResetToken {
	private static final int EXPIRATION = 60*24;
	
	//primary key
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	private Date expiryDate;
	
	//Constructor
	public PasswordResetToken(final String token, final User user) {
		super();
		
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	//Calculate the expiration date
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE,  expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
	
	public void updateToken (final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
}
