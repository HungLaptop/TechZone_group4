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
import util.EmailUtils;
import util.OTPUtils;
import util.PasswordUtils;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        // 1. Validate
        if (!password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 2. Check email tồn tại
        AccountDAO dao = new AccountDAO();
        if (dao.checkEmailExisted(email)) {
            request.setAttribute("error", "Email đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 3. Generate OTP
        String otp = OTPUtils.generateOTP();

        // 4. Lưu session
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("otp_fullname", fullname);
        session.setAttribute("otp_email", email);
        session.setAttribute("otp_password", password);
        session.setAttribute("otp_time", System.currentTimeMillis());
        

        // 5. Gửi email
        try {
            EmailUtils.sendOTP(email, otp);
        } catch (Exception e) {
            request.setAttribute("error", "Không gửi được email!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // DEBUG (xem OTP trong console nếu mail lỗi)
        System.out.println("OTP = " + otp);

        // 6. Redirect sang verify
        response.sendRedirect("verify");
    }
}
