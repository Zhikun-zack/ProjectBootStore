package com.bookstore.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.domain.security.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	//this method will delete all expired token
	@Modifying
	@Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
	void deteteAllExpiredSince(Date now);
}
