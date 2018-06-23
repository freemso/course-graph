package edu.fudan.config;

import edu.fudan.Application;

import java.io.File;
import java.net.URISyntaxException;

public class Constants {

    /**
     * Field name in request attributes that holds current user id
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * TTL for a authorization token in hour
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;

    /**
     * Field name in request header that holds authorization token
     */
    public static final String AUTHORIZATION = "authorization";

    /**
     * Length of the id of the resources
     */
    public static final int ID_LENGTH = 7;

    /**
     * Regex for password validation
     *
     * Explanation:
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,}             # anything, at least eight places though
     * $                 # end-of-string
     */
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static final String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static final String FILE_PATH =  "/Users/freemso/Resources/Files/";

    public static final String LECTURE_PATH = "/Users/freemso/Resources/Lectures/";
}
