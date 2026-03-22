package com.techzone.app.service.impl;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Cart;
import com.techzone.app.entity.CartItem;
import com.techzone.app.entity.Product;
import com.techzone.app.repository.CartRepository;
import com.techzone.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart getCart(AppUser user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Override
    public Cart addItem(AppUser user, Product product, int quantity) {
        Cart cart = getCart(user);
        CartItem existing = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
        if (existing == null) {
            CartItem item = CartItem.builder().product(product).quantity(quantity).cart(cart).build();
            cart.getItems().add(item);
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItem(AppUser user, Long itemId, int quantity) {
        Cart cart = getCart(user);
        cart.getItems().stream().filter(i -> i.getId().equals(itemId)).findFirst().ifPresent(i -> i.setQuantity(quantity));
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItem(AppUser user, Long itemId) {
        Cart cart = getCart(user);
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(AppUser user) {
        Cart cart = getCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}