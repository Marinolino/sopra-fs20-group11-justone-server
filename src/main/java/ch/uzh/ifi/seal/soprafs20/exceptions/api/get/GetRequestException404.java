package ch.uzh.ifi.seal.soprafs20.exceptions.api.get;

import ch.uzh.ifi.seal.soprafs20.exceptions.api.ApiRequestException;

public class GetRequestException404 extends ApiRequestException {

    public GetRequestException404(String message){
        super(message);
    }

    public GetRequestException404(String message, Throwable cause){
        super(message, cause);
    }
}
