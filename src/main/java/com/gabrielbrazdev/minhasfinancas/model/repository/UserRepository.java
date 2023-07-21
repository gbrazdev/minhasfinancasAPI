package com.gabrielbrazdev.minhasfinancas.model.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.gabrielbrazdev.minhasfinancas.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
	boolean existsByEmail(String email);
}
