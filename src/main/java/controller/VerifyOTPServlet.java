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

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class VerifyOTPServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("verify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String inputOtp = request.getParameter("otp");

        HttpSession session = request.getSession();
        String fullname = (String) session.getAttribute("otp_fullname");
        String otp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("otp_email");
        String password = (String) session.getAttribute("otp_password");
        Long time = (Long) session.getAttribute("otp_time");

        // check hết hạn
        if (time == null || System.currentTimeMillis() - time > 2 * 60 * 1000) {
            request.setAttribute("error", "OTP hết hạn!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
            return;
        }

        // check đúng OTP
        if (otp != null && otp.equals(inputOtp)) {

            AccountDAO dao = new AccountDAO();
            dao.register(email, password, fullname);

            // 🔥 THÊM THÔNG BÁO
            request.setAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");

            // clear session
            session.removeAttribute("otp");
            session.removeAttribute("otp_email");
            session.removeAttribute("otp_password");
            session.removeAttribute("otp_time");
            session.removeAttribute("otp_fullname");
            // 👉 forward (KHÔNG redirect)
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } else {
            request.setAttribute("error", "OTP sai!");
            request.getRequestDispatcher("verify.jsp").forward(request, response);
        }
    }
}
