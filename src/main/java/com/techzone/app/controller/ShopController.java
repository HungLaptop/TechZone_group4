package com.techzone.app.controller;

import com.techzone.app.entity.*;
import com.techzone.app.repository.UserRepository;
import com.techzone.app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final FeedbackService feedbackService;
    private final VoucherService voucherService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SupplierService supplierService;
    private final UserRepository userRepository;

    private AppUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || auth.getName().equals("anonymousUser")) {
            throw new IllegalStateException("User not authenticated");
        }
        return userRepository.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping("/customer/products")
    public String products(@RequestParam(required = false) String q,
                           @RequestParam(required = false) Long category,
                           @RequestParam(required = false) Long brand,
                           @RequestParam(required = false) Long supplier,
                           @RequestParam(required = false) Double minPrice,
                           @RequestParam(required = false) Double maxPrice,
                           @RequestParam(required = false) String sort,
                           Model model) {
        List<Product> products = productService.search(q, category, brand, supplier, minPrice, maxPrice, sort);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("brands", brandService.listAll());
        model.addAttribute("suppliers", supplierService.listAll());
        model.addAttribute("query", q);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedBrand", brand);
        model.addAttribute("selectedSupplier", supplier);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sort", sort);
        return "customer/products";
    }

    @GetMapping("/customer/products/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        List<Feedback> feedbackList = feedbackService.listByProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("feedbackList", feedbackList);
        model.addAttribute("vouchers", voucherService.listAvailable());
        return "customer/product-detail";
    }

    @PostMapping("/customer/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int qty) {
        AppUser user = getCurrentUser();
        Product product = productService.getById(productId);
        cartService.addItem(user, product, qty);
        return "redirect:/customer/cart";
    }

    @GetMapping("/customer/cart")
    public String viewCart(Model model) {
        AppUser user = getCurrentUser();
        Cart cart = cartService.getCart(user);
        double total = cart.getItems().stream().mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "customer/cart";
    }

    @PostMapping("/customer/cart/update")
    public String updateCart(@RequestParam Long itemId, @RequestParam int quantity) {
        AppUser user = getCurrentUser();
        cartService.updateItem(user, itemId, quantity);
        return "redirect:/customer/cart";
    }

    @PostMapping("/customer/cart/remove")
    public String removeCartItem(@RequestParam Long itemId) {
        AppUser user = getCurrentUser();
        cartService.removeItem(user, itemId);
        return "redirect:/customer/cart";
    }

    @PostMapping("/customer/cart/checkout")
    public String checkout(@RequestParam(required = false) String voucherCode) {
        AppUser user = getCurrentUser();
        Order order = orderService.placeOrder(user);
        if (voucherCode != null && !voucherCode.isBlank()) {
            Voucher voucher = voucherService.assignToUser(voucherCode, user);
            // placeholder: since real discount not applied in current flow.
            order.setTotal(order.getTotal() - voucher.getDiscountValue() - (order.getTotal() * voucher.getDiscountPercent() / 100));
        }
        return "redirect:/customer/orders";
    }

    @GetMapping("/customer/orders")
    public String viewOrders(Model model) {
        AppUser user = getCurrentUser();
        model.addAttribute("orders", orderService.findUserOrders(user));
        return "customer/orders";
    }

    @GetMapping("/customer/orders/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getById(id);
        model.addAttribute("order", order);
        return "customer/order-detail";
    }

    @PostMapping("/customer/orders/{id}/cancel")
    public String cancelOrder(@PathVariable Long id) {
        Order order = orderService.getById(id);
        order.setStatus(OrderStatus.CANCELLED);
        // save directly through repository by injected orderService if available
        return "redirect:/customer/orders";
    }

    @GetMapping("/customer/address")
    public String addressList(Model model) {
        AppUser user = getCurrentUser();
        model.addAttribute("addresses", addressService.listByUser(user));
        return "customer/address";
    }

    @PostMapping("/customer/address/add")
    public String addAddress(Address address) {
        AppUser user = getCurrentUser();
        address.setUser(user);
        addressService.save(address);
        return "redirect:/customer/address";
    }

    @PostMapping("/customer/address/update")
    public String updateAddress(Address address) {
        address.setUser(getCurrentUser());
        addressService.save(address);
        return "redirect:/customer/address";
    }

    @PostMapping("/customer/address/delete")
    public String deleteAddress(@RequestParam Long id) {
        addressService.delete(id);
        return "redirect:/customer/address";
    }

    @PostMapping("/customer/address/default")
    public String setDefaultAddress(@RequestParam Long id) {
        addressService.setDefaultAddress(getCurrentUser(), id);
        return "redirect:/customer/address";
    }

    @GetMapping("/customer/feedback")
    public String feedbackList(@RequestParam(required = false) Long productId, Model model) {
        if (productId != null) {
            Product product = productService.getById(productId);
            model.addAttribute("feedbacks", feedbackService.listByProduct(product));
        } else {
            model.addAttribute("feedbacks", List.of());
        }
        return "customer/feedback";
    }

    @PostMapping("/customer/feedback")
    public String writeFeedback(@RequestParam Long productId,
                                @RequestParam int rating,
                                @RequestParam String comment) {
        AppUser user = getCurrentUser();
        Feedback feedback = Feedback.builder()
                .user(user)
                .product(productService.getById(productId))
                .rating(rating)
                .comment(comment)
                .build();
        feedbackService.postFeedback(feedback);
        return "redirect:/customer/products/" + productId;
    }

    @GetMapping("/staff/feedback")
    public String staffFeedback(Model model) {
        model.addAttribute("pending", feedbackService.listPendingFeedback());
        return "staff/feedback";
    }

    @GetMapping("/customer/vouchers")
    public String customerVouchers(Model model) {
        AppUser user = getCurrentUser();
        model.addAttribute("vouchers", voucherService.listForUser(user));
        model.addAttribute("available", voucherService.listAvailable());
        return "customer/vouchers";
    }

    @GetMapping("/staff/manage/vouchers")
    public String manageVouchers(Model model) {
        model.addAttribute("all", voucherService.listAvailable());
        return "staff/vouchers";
    }

    @PostMapping("/staff/manage/vouchers")
    public String createVoucher(@RequestParam String code,
                                @RequestParam double discountPercent,
                                @RequestParam double discountValue) {
        Voucher voucher = Voucher.builder()
                .code(code)
                .discountPercent(discountPercent)
                .discountValue(discountValue)
                .active(true)
                .build();
        voucherService.createVoucher(voucher);
        return "redirect:/staff/manage/vouchers";
    }

    @PostMapping("/staff/manage/vouchers/assign")
    public String assignVoucher(@RequestParam String code,
                                @RequestParam String username) {
        AppUser user = userRepository.findByUsername(username).orElseThrow();
        voucherService.assignToUser(code, user);
        return "redirect:/staff/manage/vouchers";
    }

    @PostMapping("/staff/feedback/approve")
    public String approveFeedback(@RequestParam Long id) {
        feedbackService.approveFeedback(id);
        return "redirect:/staff/feedback";
    }

    @PostMapping("/staff/feedback/reply")
    public String replyFeedback(@RequestParam Long id, @RequestParam String reply) {
        feedbackService.replyFeedback(id, reply);
        return "redirect:/staff/feedback";
    }

    @GetMapping("/staff/import-stock")
    public String viewImportStock(Model model) {
        model.addAttribute("history", voucherService.listAvailable());
        return "staff/import-stock";
    }

    @PostMapping("/staff/import-stock")
    public String addImportStock(@RequestParam Long productId, @RequestParam int quantity) {
        AppUser user = getCurrentUser();
        Product product = productService.getById(productId);
        // record and update stock
        product.setStock(product.getStock() + quantity);
        productService.save(product);
        return "redirect:/staff/import-stock";
    }

    @GetMapping("/admin/dashboard/stats")
    public String adminStats(Model model) {
        model.addAttribute("productCount", productService.listAll().size());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("orderCount", orderService.findUserOrders(getCurrentUser()).size());
        return "admin/dashboard";
    }

    @GetMapping("/staff/dashboard/stats")
    public String staffStats(Model model) {
        model.addAttribute("inventoryCount", productService.listAll().stream().mapToInt(Product::getStock).sum());
        return "staff/dashboard";
    }
}
