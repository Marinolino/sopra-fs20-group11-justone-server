package ch.uzh.ifi.seal.soprafs20.exceptions.API;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException (String message, HttpStatus statusCode){
        super(message);
    }

    public ApiRequestException (String message, Throwable cause){
        super(message, cause);
    }

}
