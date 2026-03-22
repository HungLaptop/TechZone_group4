/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Account;
import util.DBContext;

import java.sql.*;
import java.security.MessageDigest;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class AccountDAO {

    public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Account login(String email, String password) {
        String sql = "SELECT * FROM Accounts WHERE Email = ? AND PasswordHash = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, getMD5(password));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("AccountID"),
                        rs.getString("Email"),
                        rs.getString("FullName"),
                        rs.getString("PasswordHash"),
                        rs.getString("Role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void register(String email, String password, String fullname) {
        String sql = "INSERT INTO Accounts (Email, PasswordHash, FullName, Role) VALUES (?, ?, ?, ?)";

        try (
                 Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            String hash = getMD5(password);

            ps.setString(1, email);
            ps.setString(2, hash);
            ps.setString(3, fullname);
            ps.setString(4, "user");

            int rows = ps.executeUpdate();

            System.out.println(">>> INSERT RESULT: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkEmailExisted(String email) {
        String sql = "SELECT * FROM Accounts WHERE Email = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // có data = tồn tại

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
