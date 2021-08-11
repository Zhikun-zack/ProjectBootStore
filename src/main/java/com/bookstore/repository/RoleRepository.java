package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.security.Role;

//Interface for generic CRUD operations on a repository for a specific type
//param: Role: class of entity, Long is the type if ID
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}

