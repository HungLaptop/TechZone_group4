/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

import model.CartItem;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {

            Iterator<CartItem> iterator = cart.iterator();

            while (iterator.hasNext()) {
                CartItem item = iterator.next();

                if (item.getId() == id) {

                    if (quantity <= 0) {
                        iterator.remove(); // remove item
                    } else {
                        item.setQuantity(quantity); // update
                    }
                    break;
                }
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("cart");
    }
}
