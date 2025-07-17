package com.nm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nm.entity.Users;
import com.nm.entity.Violation;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Long> {
    List<Violation> findByUserOrderByCreatedAtDesc(Users user);
	long countByUser(Users user);
}

