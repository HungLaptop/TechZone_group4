package com.techzone.app.controller;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Order;
import com.techzone.app.entity.Product;
import com.techzone.app.entity.Cart;
import com.techzone.app.service.CartService;
import com.techzone.app.service.OrderService;
import com.techzone.app.service.PaymentService;
import com.techzone.app.repository.ProductRepository;
import com.techzone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class ApiOrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private AppUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalArgumentException("Unauthenticated");
        }
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCart() {
        AppUser user = getCurrentUser();
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PostMapping("/cart/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Map<String, Object> body) {
        AppUser user = getCurrentUser();
        Long productId = Long.valueOf(body.get("productId").toString());
        int quantity = Integer.parseInt(body.getOrDefault("quantity", 1).toString());
        Product product = productRepository.findById(productId).orElseThrow();
        return ResponseEntity.ok(cartService.addItem(user, product, quantity));
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestBody Map<String, Object> body) {
        AppUser user = getCurrentUser();
        Long itemId = Long.valueOf(body.get("itemId").toString());
        return ResponseEntity.ok(cartService.removeItem(user, itemId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String, String> body) {
        AppUser user = getCurrentUser();
        Order order = orderService.placeOrder(user);
        String method = body.getOrDefault("method", "CARD");
        var payment = paymentService.processPayment(order, method);
        return ResponseEntity.ok(Map.of("order", order, "payment", payment));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        AppUser user = getCurrentUser();
        return ResponseEntity.ok(orderService.findUserOrders(user));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }
}
