package com.a4586.primo.primoscoutingapp;

/**
 * Created by yaniv on 03/03/2018.
 */

public class GameComment {
    private String gameNum;
    private String Comment;

    public GameComment(String gameNum,String comment) {
        this.gameNum = gameNum;
        this.Comment = comment;
    }

    public String getGameNum() {
        return gameNum;
    }

    public void setGameNum(String gameNum) {
        this.gameNum = gameNum;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
