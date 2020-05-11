package ch.uzh.ifi.seal.soprafs20.exceptions.api.get;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiException;
import org.springframework.http.HttpStatus;

    public class GetException extends ApiException {

        public GetException(String message, HttpStatus statusCode){
            super(message, statusCode);
        }

    }
