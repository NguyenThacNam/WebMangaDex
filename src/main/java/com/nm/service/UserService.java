package com.nm.service;

import com.nm.entity.Users;
import com.nm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    // Lấy tất cả người dùng
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    // Tìm người dùng theo ID
    public Users getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    // Tìm người dùng theo username
    public Users getUserByUsername(String username) {
        return userRepo.findUserByUsername(username).orElse(null);
    }

    // Kiểm tra trạng thái khóa
    public boolean isUserLocked(Users user) {
        return user.isLocked();
    }

    // Mở khóa tài khoản
    public void unlockUser(Long userId) {
        Optional<Users> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            user.setLockedUntil(null);
            userRepo.save(user);
        }
    }

    // Khóa tài khoản trong X giờ
    public void lockUser(Long userId, int hours) {
        Optional<Users> userOpt = userRepo.findById(userId);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            user.setLockedUntil(LocalDateTime.now().plusHours(hours));
            userRepo.save(user);
        }
    }
}
