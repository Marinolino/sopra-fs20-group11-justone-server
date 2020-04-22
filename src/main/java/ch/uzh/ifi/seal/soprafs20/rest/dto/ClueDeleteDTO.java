package ch.uzh.ifi.seal.soprafs20.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ClueDeleteDTO {

    List<String> cluesToDelete = new ArrayList<>();

    public List<String> getCluesToDelete(){
        return cluesToDelete;
    }

    public void setCluesToDelete(List<String> cluesToDelete){
        this.cluesToDelete = cluesToDelete;
    }
}
