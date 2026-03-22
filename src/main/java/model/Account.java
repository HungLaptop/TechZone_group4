/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class Account {
    private int accountID;
    private String email;
    private String fullName;
    private String passwordHash;
    private String role;

    public Account() {}

    public Account(int accountID, String email, String fullName, String passwordHash, String role) {
        this.accountID = accountID;
        this.email = email;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getAccountID() { return accountID; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }

    public void setFullName(String fullName) { this.fullName = fullName; }
}