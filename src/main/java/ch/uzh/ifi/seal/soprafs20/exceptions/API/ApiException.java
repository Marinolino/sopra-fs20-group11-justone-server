package ch.uzh.ifi.seal.soprafs20.exceptions.API;

import org.springframework.http.HttpStatus;

public class ApiException {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage(){
        return this.message;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
