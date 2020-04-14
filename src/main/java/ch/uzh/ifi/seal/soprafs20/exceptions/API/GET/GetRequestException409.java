package ch.uzh.ifi.seal.soprafs20.exceptions.API.GET;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class GetRequestException409 extends ApiRequestException {

    public GetRequestException409(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public GetRequestException409(String message, Throwable cause){
        super(message, cause);
    }
}
