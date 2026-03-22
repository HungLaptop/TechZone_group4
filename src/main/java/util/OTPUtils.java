/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.security.SecureRandom;

/**
 *
 * @author Dang Vinh Hung - CE170162
 */
public class OTPUtils {

    private static final SecureRandom rand = new SecureRandom();

    public static String generateOTP() {
        int otp = 100000 + rand.nextInt(900000);
        return String.valueOf(otp);
    }
}
