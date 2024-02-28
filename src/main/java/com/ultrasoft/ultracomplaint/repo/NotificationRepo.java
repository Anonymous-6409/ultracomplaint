package com.ultrasoft.ultracomplaint.repo;

import com.ultrasoft.ultracomplaint.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Tbl_Notification, Long> {
    List<Tbl_Notification> findByActive(Boolean isActive);
}
