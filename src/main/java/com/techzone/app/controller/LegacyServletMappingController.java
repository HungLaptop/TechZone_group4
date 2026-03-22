package com.techzone.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LegacyServletMappingController {

    @GetMapping("/LoginServlet")
    public String login() {
        return "redirect:/login";
    }

    @PostMapping("/LoginServlet")
    public String loginPost() {
        return "redirect:/perform_login";
    }

    @GetMapping("/RegisterServlet")
    public String register() {
        return "redirect:/register";
    }

    @PostMapping("/RegisterServlet")
    public String registerPost(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password) {
        return "redirect:/register"; // handled by AuthController
    }

    @GetMapping("/ForgotPasswordServlet")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/ForgotPasswordServlet")
    public String forgotPasswordPost(@RequestParam String email) {
        return "redirect:/login?forgot=true";
    }

    @GetMapping("/ResetPasswordServlet")
    public String resetPassword() {
        return "reset-password";
    }

    @PostMapping("/ResetPasswordServlet")
    public String resetPasswordPost(@RequestParam String token,
                                    @RequestParam String password) {
        return "redirect:/login?reset=true";
    }

    @GetMapping("/LoginGoogleServlet")
    public String loginGoogle() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/LogoutServlet")
    public String logout() {
        return "redirect:/logout";
    }

    @GetMapping("/LoginStaffServlet")
    public String loginStaff() {
        return "redirect:/login";
    }

    @GetMapping("/LoginAdminServlet")
    public String loginAdmin() {
        return "redirect:/login";
    }

    @GetMapping("/ChangePasswordServlet")
    public String changePassword() {
        return "redirect:/login";
    }

    @GetMapping("/ChangePasswordStaffServlet")
    public String changePasswordStaff() {
        return "redirect:/login";
    }

    @GetMapping("/ViewProfileServlet")
    public String viewProfile() {
        return "redirect:/customer/dashboard";
    }

    @GetMapping("/HomeServlet")
    public String homeServlet() {
        return "redirect:/";
    }

    @GetMapping("/ProductListServlet")
    public String productList() {
        return "redirect:/customer/products";
    }

    @GetMapping("/SearchProductServlet")
    public String searchProduct(@RequestParam(required = false) String q) {
        return "redirect:/customer/products";
    }

    @GetMapping("/FilterProductServlet")
    public String filterProduct() {
        return "redirect:/customer/products";
    }

    @GetMapping("/SortProductServlet")
    public String sortProduct() {
        return "redirect:/customer/products";
    }

    @GetMapping("/CategoryViewServlet")
    public String categoryView() {
        return "redirect:/customer/products";
    }

    @GetMapping("/ProductDetail")
    public String productDetail() {
        return "redirect:/customer/products";
    }

    @GetMapping("/ImageProductDetail")
    public String productImages() {
        return "redirect:/customer/products";
    }

    @PostMapping("/AddCartServlet")
    public String addCart() {
        return "redirect:/customer/products";
    }

    @GetMapping("/CartListServlet")
    public String cartList() {
        return "redirect:/customer/cart";
    }

    @PostMapping("/UpdateCartServlet")
    public String updateCart() {
        return "redirect:/customer/cart";
    }

    @PostMapping("/RemoveCartItemServlet")
    public String removeCartItem() {
        return "redirect:/customer/cart";
    }

    @PostMapping("/VoucherOrderServlet")
    public String voucherOrder() {
        return "redirect:/customer/cart";
    }

    @PostMapping("/CheckoutServlet")
    public String checkout() {
        return "redirect:/customer/orders";
    }

    @GetMapping("/ViewOrderOfCustomerServlet")
    public String viewOrders() {
        return "redirect:/customer/orders";
    }

    @GetMapping("/CustomerOrderDetailServlet")
    public String customerOrderDetail() {
        return "redirect:/customer/orders";
    }

    @PostMapping("/CancelOrderServlet")
    public String cancelOrder() {
        return "redirect:/customer/orders";
    }

    @GetMapping("/AddressListServlet")
    public String addressList() {
        return "redirect:/customer/address";
    }

    @PostMapping("/AddAddressServlet")
    public String addAddress() {
        return "redirect:/customer/address";
    }

    @PostMapping("/UpdateAddressServlet")
    public String updateAddress() {
        return "redirect:/customer/address";
    }

    @PostMapping("/DeleteAddressServlet")
    public String deleteAddress() {
        return "redirect:/customer/address";
    }

    @PostMapping("/SetDefaultAddressServlet")
    public String setDefaultAddress() {
        return "redirect:/customer/address";
    }

    @PostMapping("/WriteFeedbackServlet")
    public String writeFeedback() {
        return "redirect:/customer/feedback";
    }

    @GetMapping("/ViewListNewFeedbackServlet")
    public String viewNewFeedback() {
        return "redirect:/staff/feedback";
    }

    @GetMapping("/ViewFeedBackForStaffServlet")
    public String viewFeedbackForStaff() {
        return "redirect:/staff/feedback";
    }

    @PostMapping("/ReplyFeedbackServlet")
    public String replyFeedback() {
        return "redirect:/staff/feedback";
    }

    @PostMapping("/UpdateReplyServlet")
    public String updateReply() {
        return "redirect:/staff/feedback";
    }

    @PostMapping("/DeleteReplyServlet")
    public String deleteReply() {
        return "redirect:/staff/feedback";
    }

    @PostMapping("/UpdateStatusCommentServlet")
    public String updateStatusComment() {
        return "redirect:/staff/feedback";
    }

    @GetMapping("/VoucherServlet")
    public String getVoucher() {
        return "redirect:/staff/manage/vouchers";
    }

    @PostMapping("/AssignVoucherServlet")
    public String assignVoucher() {
        return "redirect:/staff/manage/vouchers";
    }

    @PostMapping("/AddPromotionServlet")
    public String addPromotion() {
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/ProductListForStaff")
    public String productListStaff() {
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/AdminViewProductDetailServlet")
    public String adminViewProductDetail() {
        return "redirect:/staff/manage/products";
    }

    @PostMapping("/AdminAddProduct")
    public String adminAddProduct() {
        return "redirect:/staff/manage/products";
    }

    @PostMapping("/AdminAddProductDetailServlet")
    public String adminAddProductDetail() {
        return "redirect:/staff/manage/products";
    }

    @PostMapping("/AdminUpdateProductServlet")
    public String adminUpdateProduct() {
        return "redirect:/staff/manage/products";
    }

    @PostMapping("/AdminDeleteProductServlet")
    public String adminDeleteProduct() {
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/UpdateCategoryServlet")
    public String updateCategory() {
        return "redirect:/staff/manage/category";
    }

    @PostMapping("/CreateCategory")
    public String createCategory() {
        return "redirect:/staff/manage/category";
    }

    @PostMapping("/CategoryDetail")
    public String categoryDetail() {
        return "redirect:/staff/manage/category";
    }

    @GetMapping("/ViewSupplierServlet")
    public String viewSupplier() {
        return "redirect:/staff/manage/supplier";
    }

    @PostMapping("/CreateSupplierServlet")
    public String createSupplier() {
        return "redirect:/staff/manage/supplier";
    }

    @PostMapping("/UpdateSupplierServlet")
    public String updateSupplier() {
        return "redirect:/staff/manage/supplier";
    }

    @PostMapping("/DeleteSupplierServlet")
    public String deleteSupplier() {
        return "redirect:/staff/manage/supplier";
    }

    @PostMapping("/ImportStockServlet")
    public String importStock() {
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/ViewImportStockServlet")
    public String viewImportStock() {
        return "redirect:/staff/manage/products";
    }

    @GetMapping("/InventoryStatisticServlet")
    public String inventoryStatistic() {
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/StaffListServlet")
    public String staffList() {
        return "redirect:/admin/manage/users";
    }

    @PostMapping("/CreateStaffServlet")
    public String createStaff() {
        return "redirect:/admin/manage/users";
    }

    @PostMapping("/UpdateStaffServlet")
    public String updateStaff() {
        return "redirect:/admin/manage/users";
    }

    @PostMapping("/DeleteStaffServlet")
    public String deleteStaff() {
        return "redirect:/admin/manage/users";
    }

    @GetMapping("/CustomerListServlet")
    public String customerList() {
        return "redirect:/admin/manage/users";
    }

    @GetMapping("/ViewOrderListServlet")
    public String viewAllOrders() {
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/ViewOrderDetailServlet")
    public String viewOrderDetail() {
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/AdminDashboard")
    public String adminDashboard() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/StaffDashboardServlet")
    public String staffDashboard() {
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/RevenueStatisticServlet")
    public String revenueStatistic() {
        return "redirect:/staff/dashboard";
    }

    @GetMapping("/ImportStatisticServlet")
    public String importStatistic() {
        return "redirect:/staff/dashboard";
    }
}
