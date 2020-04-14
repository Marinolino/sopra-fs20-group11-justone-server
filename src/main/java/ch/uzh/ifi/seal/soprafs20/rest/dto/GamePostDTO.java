package ch.uzh.ifi.seal.soprafs20.rest.dto;

import ch.uzh.ifi.seal.soprafs20.entity.User;

import java.util.List;

public class GamePostDTO {

    private List<Long> userIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
