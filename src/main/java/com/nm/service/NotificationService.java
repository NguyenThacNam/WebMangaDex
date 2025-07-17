package com.nm.service;

import com.nm.entity.Users;
import com.nm.entity.Notification;
import com.nm.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;

    // Gửi thông báo
    public Notification sendNotification(Users user, String message) {
        Notification noti = new Notification();
        noti.setUser(user);
        noti.setMessage(message);
        return notificationRepo.save(noti);
    }

    // Lấy danh sách thông báo của người dùng
    public List<Notification> getNotificationsByUser(Users user) {
        return notificationRepo.findByUserOrderByCreatedAtDesc(user);
    }

    // Đánh dấu đã đọc
    public void markAsRead(Long id) {
        Notification noti = notificationRepo.findById(id).orElse(null);
        if (noti != null) {
            noti.setRead(true);
            notificationRepo.save(noti);
        }
    }

    // Xóa thông báo
    public void deleteNotification(Long id) {
        notificationRepo.deleteById(id);
    }

    // Đếm số thông báo chưa đọc
    public long countUnread(Users user) {
        return notificationRepo.countByUserAndReadFalse(user);
    }
}
