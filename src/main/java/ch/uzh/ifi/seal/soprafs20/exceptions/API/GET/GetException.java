package ch.uzh.ifi.seal.soprafs20.exceptions.API.GET;

import ch.uzh.ifi.seal.soprafs20.exceptions.API.ApiException;
import org.springframework.http.HttpStatus;

    public class GetException extends ApiException {

        public GetException(String message, HttpStatus statusCode){
            super(message, statusCode);
        }

    }
