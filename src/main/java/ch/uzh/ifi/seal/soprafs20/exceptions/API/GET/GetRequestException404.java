package ch.uzh.ifi.seal.soprafs20.exceptions.API.GET;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiRequestException;
import org.springframework.http.HttpStatus;

public class GetRequestException404 extends ApiRequestException {

    public GetRequestException404(String message, HttpStatus statusCode){
        super(message, statusCode);
    }

    public GetRequestException404(String message, Throwable cause){
        super(message, cause);
    }
}
