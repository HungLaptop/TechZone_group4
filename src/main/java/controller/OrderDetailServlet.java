/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;
import util.DBContext;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id_raw = request.getParameter("id");

        if (id_raw == null) {
            response.sendRedirect("orders");
            return;
        }

        int orderId = Integer.parseInt(id_raw);

        List<OrderDetail> list = new ArrayList<>();

        try ( Connection conn = new DBContext().getConnection()) {

            String sql = "SELECT p.Name, od.Price, od.Quantity "
                    + "FROM OrderDetails od "
                    + "JOIN Products p ON od.ProductID = p.ProductID "
                    + "WHERE od.OrderID = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new OrderDetail(
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("details", list);
        request.setAttribute("orderId", orderId);

        request.getRequestDispatcher("order-detail.jsp").forward(request, response);
    }
}
