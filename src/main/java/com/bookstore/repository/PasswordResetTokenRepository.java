package com.bookstore.repository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
	Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);
	
	@Modify
	@Query("delete from PasswordResetToken t where t.expirydate <= ?1")
	void deteteAllExpiredSince(Date now);
}
