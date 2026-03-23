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

import model.Account;
import model.Order;
import util.DBContext;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class OrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        List<Order> list = new ArrayList<>();

        try ( Connection conn = new DBContext().getConnection()) {

            String sql = "SELECT OrderID, Total, OrderDate, Status FROM Orders WHERE AccountID = ? ORDER BY OrderID DESC";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, acc.getAccountID());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("OrderID"),
                        rs.getDouble("Total"),
                        rs.getTimestamp("CreatedAt"),
                        rs.getString("Status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("orders", list);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
}
