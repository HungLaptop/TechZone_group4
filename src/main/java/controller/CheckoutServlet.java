/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 🔹 lấy cart
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        try (Connection conn = new DBContext().getConnection()) {

            // 🔥 1. Tính total
            ProductDAO dao = new ProductDAO();
            double total = 0;

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                total += dao.getById(entry.getKey()).getPrice() * entry.getValue();
            }

            // 🔥 2. Insert Orders
            String sqlOrder = "INSERT INTO Orders (AccountID, Total) VALUES (?, ?)";
            PreparedStatement psOrder = conn.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS);

            psOrder.setInt(1, 1); // 👉 tạm hardcode user
            psOrder.setDouble(2, total);
            psOrder.executeUpdate();

            // 🔥 3. Lấy OrderID
            ResultSet rs = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // 🔥 4. Insert OrderItems
            String sqlItem = "INSERT INTO OrderItems (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {

                PreparedStatement psItem = conn.prepareStatement(sqlItem);

                int productId = entry.getKey();
                int quantity = entry.getValue();
                double price = dao.getById(productId).getPrice();

                psItem.setInt(1, orderId);
                psItem.setInt(2, productId);
                psItem.setInt(3, quantity);
                psItem.setDouble(4, price);

                psItem.executeUpdate();
            }

            // 🔥 5. Clear cart
            session.removeAttribute("cart");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔥 6. Redirect success
        response.sendRedirect("order-success");
    }
}