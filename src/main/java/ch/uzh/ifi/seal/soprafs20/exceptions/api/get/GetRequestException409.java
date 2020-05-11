package ch.uzh.ifi.seal.soprafs20.exceptions.api.get;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class GetRequestException409 extends ApiRequestException {

    public GetRequestException409(String message){
        super(message);
    }

    public GetRequestException409(String message, Throwable cause){
        super(message, cause);
    }
}
