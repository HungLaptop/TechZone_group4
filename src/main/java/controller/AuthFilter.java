/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
@WebFilter("/*") // 🔥 QUAN TRỌNG
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getRequestURI();

        HttpSession session = request.getSession(false);
        Account acc = (session != null) ? (Account) session.getAttribute("account") : null;

        // 🔓 Cho phép truy cập public
        if (url.contains("login")
                || url.contains("register")
                || url.contains("forgot")
                || url.contains("reset")
                || url.contains("products")
                || url.contains("product-detail")
                || url.contains("home")
                || url.contains("verify")
                || url.equals("/TechZone/")
                || url.equals("/TechZone")
                || url.contains("css")
                || url.contains("js")
                || url.contains("images")) {

            chain.doFilter(req, res);
            return;
        }

        // ❌ Chưa login
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        // 🔥 ADMIN
        if (url.contains("/admin")) {
            if (!acc.getRole().equals("admin")) {
                response.sendRedirect("login");
                return;
            }
        }

// STAFF
        if (url.contains("/staff")) {
            if (!acc.getRole().equals("staff")) {
                response.sendRedirect("login");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}
