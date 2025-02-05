package net.mediger.auth.api.dto;

import java.util.regex.Pattern;

public class JoinRegex {
    public static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    public static final Pattern PHONE_REGEX = Pattern.compile("^01[016789]\\d{3,4}\\d{4}$");
    public static final Pattern ACCOUNT_REGEX = Pattern.compile("^(?=.*[A-Za-z])[A-Za-z\\d]{8,}$");
    public static final Pattern PASSWORD_REGEX = Pattern.compile(
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,20})");
}
