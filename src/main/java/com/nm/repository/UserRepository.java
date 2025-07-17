package com.nm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nm.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String usename);
	Optional<Users> findUserByUsername(String username);


}
