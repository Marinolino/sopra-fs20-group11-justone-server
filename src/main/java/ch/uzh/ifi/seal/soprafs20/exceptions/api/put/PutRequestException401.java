package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException401 extends ApiRequestException {

    public PutRequestException401(String message){
        super(message);
    }

    public PutRequestException401(String message, Throwable cause){
        super(message, cause);
    }
}
