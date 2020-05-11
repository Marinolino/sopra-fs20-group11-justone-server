package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiException;
import org.springframework.http.HttpStatus;

public class PutException extends ApiException {

    public PutException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

}
