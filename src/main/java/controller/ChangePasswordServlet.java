/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");

        AccountDAO dao = new AccountDAO();

        if (!dao.checkPassword(acc.getEmail(), oldPass)) {
            request.setAttribute("error", "Wrong old password");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        dao.changePassword(acc.getAccountID(), newPass);

        request.setAttribute("success", "Password updated!");
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
