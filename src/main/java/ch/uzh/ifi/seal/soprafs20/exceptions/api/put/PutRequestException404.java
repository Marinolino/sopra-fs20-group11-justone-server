package ch.uzh.ifi.seal.soprafs20.exceptions.api.put;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class PutRequestException404 extends ApiRequestException {

    public PutRequestException404(String message){
        super(message);
    }

    public PutRequestException404(String message, Throwable cause){
        super(message, cause);
    }
}
