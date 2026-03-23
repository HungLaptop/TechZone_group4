/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import model.CartItem;
import model.Account;
import util.DBContext;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class CheckoutServlet extends HttpServlet {

    // 🔥 1. HIỂN THỊ TRANG CHECKOUT
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Account acc = (Account) session.getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // 👉 chỉ hiển thị page thôi (KHÔNG insert DB nữa)
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    // 🔥 2. XỬ LÝ ĐẶT HÀNG
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Account acc = (Account) session.getAttribute("account");
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        try ( Connection conn = new DBContext().getConnection()) {

            // 🔥 1. Tính total
            double total = 0;
            for (CartItem item : cart) {
                total += item.getPrice() * item.getQuantity();
            }

            // 🔥 2. Insert Orders
            String sqlOrder = "INSERT INTO Orders (AccountID, Total) VALUES (?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);

            psOrder.setInt(1, acc.getAccountID());
            psOrder.setDouble(2, total);
            psOrder.executeUpdate();

            // 🔥 3. Lấy OrderID
            ResultSet rs = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // 🔥 4. Insert OrderDetails
            String sqlDetail = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";

            for (CartItem item : cart) {

                PreparedStatement ps = conn.prepareStatement(sqlDetail);

                ps.setInt(1, orderId);
                ps.setInt(2, item.getId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getPrice());

                ps.executeUpdate();
            }

            // 🔥 5. Clear cart
            session.removeAttribute("cart");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔥 6. Redirect orders
        response.sendRedirect("orders");
    }
}
