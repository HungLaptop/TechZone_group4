package com.techzone.app.service.impl;

import com.techzone.app.entity.Order;
import com.techzone.app.entity.Payment;
import com.techzone.app.repository.PaymentRepository;
import com.techzone.app.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment processPayment(Order order, String method) {
        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotal())
                .method(method)
                .status("PAID")
                .paidAt(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);
    }
}
