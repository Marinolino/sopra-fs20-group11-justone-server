package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException204 extends ApiRequestException {

    public PutRequestException204(String message){
        super(message);
    }

    public PutRequestException204(String message, Throwable cause){
        super(message, cause);
    }
}
