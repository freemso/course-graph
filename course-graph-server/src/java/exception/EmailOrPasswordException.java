package java.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EmailOrPasswordException extends RuntimeException {

    public EmailOrPasswordException() {
        super("Email is invalid or password is wrong.");
    }
}
