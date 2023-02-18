package ua.sirkostya009.javastuff.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CouldNotBeParsed extends RuntimeException {
    public CouldNotBeParsed(String message) {
        super(message);
    }

    public CouldNotBeParsed(String message, Throwable cause) {
        super(message, cause);
    }
}
