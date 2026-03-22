package com.techzone.app.service;

import com.techzone.app.entity.Order;
import com.techzone.app.entity.AppUser;

import java.util.List;

public interface OrderService {
    List<Order> findUserOrders(AppUser user);
    Order placeOrder(AppUser user);
    Order getById(Long id);
}
