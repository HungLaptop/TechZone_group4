package com.techzone.app.service.impl;

import com.techzone.app.entity.*;
import com.techzone.app.repository.CartRepository;
import com.techzone.app.repository.OrderRepository;
import com.techzone.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public List<Order> findUserOrders(AppUser user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order placeOrder(AppUser user) {
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        double total = 0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .build();
            order.getItems().add(orderItem);
            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        order.setTotal(total);
        Order saved = orderRepository.save(order);
        cart.getItems().clear();
        cartRepository.save(cart);

        return saved;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}
