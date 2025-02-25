package net.mediger.auth.api.dto;

public class JoinRegex {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PHONE_REGEX = "^01[016789]\\d{3,4}\\d{4}$";
    public static final String ACCOUNT_REGEX = "^(?=.*[A-Za-z])[A-Za-z\\d]{8,}$";
    public static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,20})";
}
