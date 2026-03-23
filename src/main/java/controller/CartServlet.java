/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // 🔥 1. CHECK LOGIN
        Object acc = session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        // 🔥 2. VALIDATE ID
        String id_raw = request.getParameter("id");
        if (id_raw == null) {
            // 👉 chỉ hiển thị cart
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(id_raw);
        } catch (Exception e) {
            response.sendRedirect("products");
            return;
        }

        // 🔥 3. GET PRODUCT FROM DB
        ProductDAO dao = new ProductDAO();
        Product p = dao.getById(id);

        if (p == null) {
            response.sendRedirect("products");
            return;
        }

        // 🔥 4. GET CART
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 🔥 5. CHECK EXIST
        boolean found = false;

        for (CartItem item : cart) {
            if (item.getId() == id) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        // 🔥 6. ADD NEW
        if (!found) {
            cart.add(new CartItem(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    1
            ));
        }

        // 🔥 7. SAVE SESSION
        session.setAttribute("cart", cart);

        // 🔥 8. REDIRECT CART PAGE
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // 🔥 BONUS: SUPPORT POST (CHUẨN)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}
