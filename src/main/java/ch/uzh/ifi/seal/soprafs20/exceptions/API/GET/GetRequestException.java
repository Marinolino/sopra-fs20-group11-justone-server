package ch.uzh.ifi.seal.soprafs20.exceptions.API.GET;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class GetRequestException extends ApiRequestException {

    public GetRequestException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public GetRequestException(String message, Throwable cause){
        super(message, cause);
    }
}
