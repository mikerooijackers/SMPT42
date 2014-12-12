package com.example.teamhomeplan.homeplan.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Niek on 12/12/2014.
 *
 * Utility class for various helper functions
 */
public class Utilities {

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
