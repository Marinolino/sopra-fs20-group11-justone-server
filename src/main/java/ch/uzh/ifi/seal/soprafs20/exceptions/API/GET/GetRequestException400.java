package ch.uzh.ifi.seal.soprafs20.exceptions.API.GET;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class GetRequestException400 extends ApiRequestException {

    public GetRequestException400(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public GetRequestException400(String message, Throwable cause){
        super(message, cause);
    }
}
