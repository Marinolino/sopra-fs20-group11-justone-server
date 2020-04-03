package ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PutRequestException extends ApiRequestException {

    public PutRequestException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public PutRequestException(String message, Throwable cause){
        super(message, cause);
    }
}
