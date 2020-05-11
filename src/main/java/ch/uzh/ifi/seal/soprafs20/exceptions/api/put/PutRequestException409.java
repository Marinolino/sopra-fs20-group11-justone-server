package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException409 extends ApiRequestException {
    public PutRequestException409(String message){
        super(message);
    }

    public PutRequestException409(String message, Throwable cause){
        super(message, cause);
    }
}
