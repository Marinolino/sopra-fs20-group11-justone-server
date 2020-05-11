package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException403 extends ApiRequestException {

    public PutRequestException403(String message){
        super(message);
    }

    public PutRequestException403(String message, Throwable cause){
        super(message, cause);
    }
}
