package ch.uzh.ifi.seal.soprafs20.exceptions.api;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException (String message){
        super(message);
    }

    public ApiRequestException (String message, Throwable cause){
        super(message, cause);
    }

}
