package ch.uzh.ifi.seal.soprafs20.exceptions.API.POST;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PostRequestException extends ApiRequestException {

    public PostRequestException(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public PostRequestException(String message, Throwable cause){
        super(message, cause);
    }
}

