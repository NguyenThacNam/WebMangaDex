package com.nm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nm.entity.Notification;
import com.nm.entity.Users;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(Users user);
    long countByUserAndMessageContaining(Users user, String keyword);
    long countByUserAndReadFalse(Users user);

}

