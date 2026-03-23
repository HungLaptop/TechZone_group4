/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class Order {

    private final int orderId;
    private final double total;
    private final Date orderDate;
    private final String status;

    public Order(int orderId, double total, Date orderDate, String status) {
        this.orderId = orderId;
        this.total = total;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotal() {
        return total;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }
}
