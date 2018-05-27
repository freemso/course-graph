package main.java.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseExceptions {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class EmailOrPasswordException extends RuntimeException {

        public EmailOrPasswordException() {
            super("Email is invalid or password is wrong.");
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class UserIdNotFoundException extends RuntimeException {

        public UserIdNotFoundException(long userId) {
            super(String.format("Could not find user '%d'.", userId));
        }
    }
}
