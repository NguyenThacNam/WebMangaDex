package com.nm.service;

import com.nm.entity.Users;
import com.nm.entity.Violation;
import com.nm.repository.ViolationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationService {

    @Autowired
    private ViolationRepository violationRepo;

    // Thêm vi phạm mới
    public Violation addViolation(Users user, String reason) {
        Violation violation = new Violation(reason, user);
        return violationRepo.save(violation);
    }

    // Lấy danh sách vi phạm của người dùng
    public List<Violation> getViolationsByUser(Users user) {
        return violationRepo.findByUserOrderByCreatedAtDesc(user);
    }

    // Đếm số lần vi phạm
    public long countViolationsByUser(Users user) {
        return violationRepo.countByUser(user);
    }

    // Xóa vi phạm (nếu cần)
    public void deleteViolation(Long id) {
        violationRepo.deleteById(id);
    }
}
