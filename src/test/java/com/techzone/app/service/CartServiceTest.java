package com.techzone.app.service;

import com.techzone.app.entity.AppUser;
import com.techzone.app.entity.Cart;
import com.techzone.app.repository.CartRepository;
import com.techzone.app.repository.ProductRepository;
import com.techzone.app.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCartWhenNotExist() {
        AppUser user = new AppUser();
        user.setUsername("customer1");

        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));

        Cart cart = cartService.getCart(user);

        assertNotNull(cart);
        assertEquals(user, cart.getUser());
        verify(cartRepository, times(1)).save(any(Cart.class));
    }
}
