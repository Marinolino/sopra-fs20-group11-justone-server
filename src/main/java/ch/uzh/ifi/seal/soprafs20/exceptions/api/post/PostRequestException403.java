package ch.uzh.ifi.seal.soprafs20.exceptions.api.post;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PostRequestException403 extends ApiRequestException {

    public PostRequestException403(String message){
        super(message);
    }

    public PostRequestException403(String message, Throwable cause){
        super(message, cause);
    }
}
