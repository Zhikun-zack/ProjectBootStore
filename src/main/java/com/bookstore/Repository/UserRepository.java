package com.bookstore.Repository;

import org.springframework.data.repository.CrudRepository;
import com.bookstore.domain.User;

public class userRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	
	User findByEmail(String email);
}
