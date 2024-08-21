package com.oficinadobrito.api.Controllers.Customvalidators.Classes;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Checks if the email is valid.
     *
     * @param email The email to be verified.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {return false;}

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
