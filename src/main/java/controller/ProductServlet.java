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
import java.util.List;
import model.Product;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();

        String search = request.getParameter("search");
        String category = request.getParameter("category");
        String price = request.getParameter("price");

        List<Product> list;

        if (search != null && !search.trim().isEmpty()) {
            list = dao.search(search);
        } else if (category != null && !category.isEmpty()) {
            list = dao.filterByCategory(category);
        } else if (price != null) {

            switch (price) {
                case "1":
                    list = dao.filterByPrice(0, 5000000);
                    break;
                case "2":
                    list = dao.filterByPrice(5000000, 10000000);
                    break;
                case "3":
                    list = dao.filterByPrice(10000000, 20000000);
                    break;
                case "4":
                    list = dao.filterByPrice(20000000, 999999999);
                    break;
                default:
                    list = dao.getAll();
            }

        } else {
            list = dao.getAll();
        }

        request.setAttribute("list", list);

        // 🔥 FIX QUAN TRỌNG
        String uri = request.getRequestURI();

        if (uri.contains("products")) {
            request.getRequestDispatcher("products.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
