package ch.uzh.ifi.seal.soprafs20.exceptions.api.get;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class GetRequestException400 extends ApiRequestException {

    public GetRequestException400(String message){
        super(message);
    }

    public GetRequestException400(String message, Throwable cause){
        super(message, cause);
    }
}
