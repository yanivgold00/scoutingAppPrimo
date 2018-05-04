package com.a4586.primo.primoscoutingapp;

/**
 * Created by yaniv on 03/03/2018.
 */

public class GameComment {

    private String gameNum; // Game number
    private String Comment; // the comment on the game

    // Constructor
    public GameComment(String gameNum, String comment) {
        this.gameNum = gameNum;
        this.Comment = comment;
    }

    public String getGameNum() {
        return gameNum;
    }

    public String getComment() {
        return Comment;
    }
}
