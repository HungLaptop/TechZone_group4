package com.techzone.app.service;

import com.techzone.app.entity.Cart;
import com.techzone.app.entity.Product;
import com.techzone.app.entity.AppUser;

public interface CartService {
    Cart getCart(AppUser user);
    Cart addItem(AppUser user, Product product, int quantity);
    Cart updateItem(AppUser user, Long itemId, int quantity);
    Cart removeItem(AppUser user, Long itemId);
    void clearCart(AppUser user);
}
