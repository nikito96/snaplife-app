package com.nn.snaplife.services;

public class InputValidator {

    public static boolean validateLogin(String email, String password) {
        int errorCount = 0;
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        email = email.trim();
        password = password.trim();

        if (!email.matches(emailRegex)) {
            errorCount++;
        }

        if (email.length() < 10 || email.length() > 30) {
            errorCount++;
        }

        if (password.isEmpty()) {
            errorCount++;
        }

        if (errorCount == 0) {
            return true;
        }

        return false;
    }

    public static boolean validateRegister(String email, String username,
                                           String password, String repeatPassword) {
        int errorCount = 0;
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        email = email.trim();
        username = username.trim();
        password = password.trim();
        repeatPassword = repeatPassword.trim();

        if (!email.matches(emailRegex)) {
            errorCount++;
        }

        if (email.length() < 10 || email.length() > 30) {
            errorCount++;
        }

        if (username.length() < 6 || username.length() > 25) {
            errorCount++;
        }

        if (password.isEmpty() || !password.equals(repeatPassword)) {
            errorCount++;
        }

        if (errorCount == 0) {
            return true;
        }

        return false;
    }
}
