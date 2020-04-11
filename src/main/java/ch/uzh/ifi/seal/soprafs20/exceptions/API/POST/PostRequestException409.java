package ch.uzh.ifi.seal.soprafs20.exceptions.API.POST;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PostRequestException409 extends ApiRequestException {

    public PostRequestException409(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public PostRequestException409(String message, Throwable cause){
        super(message, cause);
    }
}

