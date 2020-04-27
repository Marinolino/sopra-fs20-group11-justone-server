package ch.uzh.ifi.seal.soprafs20.rest.dto;

public class UserPutDTO {
    private Long id;

    private String name;

    private String username;

    private String password;

    private int correctlyGuessed;

    private int duplicateClues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCorrectlyGuessed(){
        return this.correctlyGuessed;
    }

    public void setCorrectlyGuessed(int correctlyGuessed){
        this.correctlyGuessed = correctlyGuessed;
    }

    public int getDuplicateClues(){
        return this.duplicateClues;
    }

    public void setDuplicateClues(int duplicateClues){
        this.duplicateClues = duplicateClues;
    }
}
