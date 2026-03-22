package com.techzone.app.service;

import com.techzone.app.entity.Order;
import com.techzone.app.entity.Payment;

public interface PaymentService {
    Payment processPayment(Order order, String method);
}