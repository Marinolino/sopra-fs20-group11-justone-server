package ch.uzh.ifi.seal.soprafs20.exceptions.API.POST;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiException;
import org.springframework.http.HttpStatus;

public class PostException extends ApiException {

        public PostException(String message, HttpStatus statusCode){
            super(message, statusCode);
        }
}
