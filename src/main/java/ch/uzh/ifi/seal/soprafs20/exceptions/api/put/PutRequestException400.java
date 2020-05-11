package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException400 extends ApiRequestException {

    public PutRequestException400(String message){
        super(message);
    }

    public PutRequestException400(String message, Throwable cause){
        super(message, cause);
    }
}
