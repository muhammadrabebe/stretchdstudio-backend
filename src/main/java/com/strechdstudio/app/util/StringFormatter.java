package com.strechdstudio.app.util;

public class StringFormatter {

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 8) {
            return "Invalid Number";
        }

        phoneNumber = phoneNumber.replaceAll("\\D", ""); // Remove non-numeric characters

        if (phoneNumber.startsWith("961")) {
            phoneNumber = "+" + phoneNumber; // Ensure it has the "+"
        } else if (phoneNumber.startsWith("0")) {
            phoneNumber = "+961" + phoneNumber.substring(1); // Convert "0X" to "+961X"
        } else if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+961" + phoneNumber; // Assume it's missing the country code
        }

        if (phoneNumber.length() == 12) {
            return String.format("%s %s %s %s", phoneNumber.substring(0, 4), phoneNumber.substring(4, 6),
                    phoneNumber.substring(6, 9), phoneNumber.substring(9));
        } else {
            return phoneNumber; // Fallback if formatting is unexpected
        }
    }
}
