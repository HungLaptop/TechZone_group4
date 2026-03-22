/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ProductDAO;
import model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class ProductDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy id từ URL
        String id_raw = request.getParameter("id");

        // 2. Check null
        if (id_raw == null) {
            response.sendRedirect("product"); // quay về list
            return;
        }

        // 3. Ép kiểu
        int id = Integer.parseInt(id_raw);

        // 4. Lấy data
        ProductDAO dao = new ProductDAO();
        Product p = dao.getById(id);

        // 5. Đẩy sang JSP
        request.setAttribute("product", p);

        // 6. Forward
        request.getRequestDispatcher("product-detail.jsp").forward(request, response);
    }
}