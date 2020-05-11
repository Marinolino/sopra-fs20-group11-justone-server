package ch.uzh.ifi.seal.soprafs20.exceptions.api.get;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class GetRequestException500 extends ApiRequestException {

    public GetRequestException500(String message){
        super(message);
    }

    public GetRequestException500(String message, Throwable cause){
        super(message, cause);
    }
}
