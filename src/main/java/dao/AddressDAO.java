/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.DBContext;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class AddressDAO {

    public List<String> getByAccount(int accountId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Address FROM Addresses WHERE AccountID = ?";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void add(int accountId, String address) {
        String sql = "INSERT INTO Addresses (AccountID, Address) VALUES (?, ?)";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, accountId);
            ps.setString(2, address);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
