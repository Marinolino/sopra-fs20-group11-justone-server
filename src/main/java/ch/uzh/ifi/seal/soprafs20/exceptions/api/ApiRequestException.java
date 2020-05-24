package ch.uzh.ifi.seal.soprafs20.exceptions.api;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException (String message){
        super(message);
    }

    public ApiRequestException (String message, Throwable cause){
        super(message, cause);
    }

}
