package ch.uzh.ifi.seal.soprafs20.exceptions.API.PUT;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiException;
import org.springframework.http.HttpStatus;

public class PutException extends ApiException {

    public PutException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

}
