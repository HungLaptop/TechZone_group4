package com.techzone.app.repository;

import com.techzone.app.entity.Order;
import com.techzone.app.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(AppUser user);
}
