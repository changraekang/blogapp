package com.cos.blogapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


// save(user) insert, update(id 중복)
// findById(1) 한 건 select
// findAll() List
// deleteById(1) 한 건 delete



// DAO 
public interface UserRepository  extends JpaRepository<User, Integer>{

	@Query(value = "insert into user (username, password, email) values (:username,:password ,:email )", nativeQuery = true)
	void join(String username, String password, String email);
	
	@Query(value = "select * from user where username = :username and password = :password", nativeQuery = true)
	User mLogin(String username, String password );
	
	
}
