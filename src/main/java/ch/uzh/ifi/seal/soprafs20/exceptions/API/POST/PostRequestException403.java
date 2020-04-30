package ch.uzh.ifi.seal.soprafs20.exceptions.API.POST;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PostRequestException403 extends ApiRequestException {

    public PostRequestException403(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public PostRequestException403(String message, Throwable cause){
        super(message, cause);
    }
}
