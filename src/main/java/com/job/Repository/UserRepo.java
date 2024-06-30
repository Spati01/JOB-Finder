package com.job.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.job.Entity.User;



public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String emaill);

	public User findByVerificationCode(String code);
	
}