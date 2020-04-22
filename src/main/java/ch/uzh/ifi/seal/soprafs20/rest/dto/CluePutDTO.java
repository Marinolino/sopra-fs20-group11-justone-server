package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class CluePutDTO {

    List<String> cluesToChange = new ArrayList<>();

    public List<String> getCluesToChange(){
        return cluesToChange;
    }

    public void setCluesToChange(List<String> cluesToChange){
        this.cluesToChange = cluesToChange;
    }
}
