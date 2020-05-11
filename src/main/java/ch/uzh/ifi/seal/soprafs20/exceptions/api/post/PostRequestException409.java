package ch.uzh.ifi.seal.soprafs20.exceptions.api.post;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;
import org.springframework.http.HttpStatus;

public class PostRequestException409 extends ApiRequestException {

    public PostRequestException409(String message){
        super(message);
    }

    public PostRequestException409(String message, Throwable cause){
        super(message, cause);
    }
}

