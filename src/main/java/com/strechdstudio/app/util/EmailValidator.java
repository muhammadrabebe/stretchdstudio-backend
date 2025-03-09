package com.strechdstudio.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    // Regular expression for validating email format
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    // Method to validate email format
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

