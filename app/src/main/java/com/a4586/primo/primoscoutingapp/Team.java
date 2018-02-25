package com.a4586.primo.primoscoutingapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 26/11/2017.
 */

public class Team implements Serializable {
    private int teamNumber;
    private String teamName;

    //pit scouting
    private String robotRole;
    private String roleComment;
    private Boolean doesDynamic;
    private Boolean doesStatic;
    private Boolean autoBaseLine;
    private Boolean shoots;
    private Boolean autoShoot;
    private Boolean autoGear;
    private String autoGearSide;
    private Boolean autoControlSquare;
    private Boolean endGameControlSquare;
    private String drivingSystem;
    private String wheelType;
    private Boolean visionProc;
    private String generalStrategy;
    private String issuesPotential;

    //game scouting
    private int autoGearTries;
    private int autoGearSuccesses;
    private int successfulRightAutoGear;
    private int successfulLeftAutoGear;
    private int gamesAutoShoot;
    private int timesAutoShoot;
    private int autoBaseLinePass;
    private int autoControlSquareTimes;
    private int timesPlayed;
    private int avgStaticGears;
    private int avgDynamicGears;
    private int gamesShot;
    private int avgSuccesfulShoot;
    private int timesWonMidSquare;
    private int endGameControlSquareTimes;
    private int avgSpeed;
    private int defenceTimes;
    private int timesCrashed;
    private ArrayList<String> comments;

    public Team(int teamNumber, String teamName) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.autoGearTries = 0;
        this.autoGearSuccesses = 0;
        this.successfulLeftAutoGear = 0;
        this.successfulRightAutoGear = 0;
        this.gamesAutoShoot = 0;
        this.timesAutoShoot = 0;
        this.autoBaseLinePass = 0;
        this.autoControlSquareTimes = 0;
        this.timesPlayed = 0;
        this.avgStaticGears = 0;
        this.avgDynamicGears = 0;
        this.gamesShot = 0;
        this.avgSuccesfulShoot = 0;
        this.timesWonMidSquare = 0;
        this.endGameControlSquareTimes = 0;
        this.avgSpeed = 0;
        this.defenceTimes = 0;
        this.timesCrashed = 0;
        this.comments = new ArrayList<>();
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRobotRole() {
        return robotRole;
    }

    public void setRobotRole(String robotRole) {
        this.robotRole = robotRole;
    }

    public String getRoleComment() {
        return roleComment;
    }

    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }

    public Boolean getDoesDynamic() {
        return doesDynamic;
    }

    public void setDoesDynamic(Boolean doesDynamic) {
        this.doesDynamic = doesDynamic;
    }

    public Boolean getDoesStatic() {
        return doesStatic;
    }

    public void setDoesStatic(Boolean doesStatic) {
        this.doesStatic = doesStatic;
    }

    public Boolean getAutoBaseLine() {
        return autoBaseLine;
    }

    public void setAutoBaseLine(Boolean autoBaseLine) {
        this.autoBaseLine = autoBaseLine;
    }

    public Boolean getShoots() {
        return shoots;
    }

    public void setShoots(Boolean shoots) {
        this.shoots = shoots;
    }

    public Boolean getAutoShoot() {
        return autoShoot;
    }

    public void setAutoShoot(Boolean autoShoot) {
        this.autoShoot = autoShoot;
    }

    public Boolean getAutoGear() {
        return autoGear;
    }

    public void setAutoGear(Boolean autoGear) {
        this.autoGear = autoGear;
    }

    public String getAutoGearSide() {
        return autoGearSide;
    }

    public void setAutoGearSide(String autoGearSide) {
        this.autoGearSide = autoGearSide;
    }

    public Boolean getAutoControlSquare() {
        return autoControlSquare;
    }

    public void setAutoControlSquare(Boolean autoControlSquare) {
        this.autoControlSquare = autoControlSquare;
    }

    public Boolean getEndGameControlSquare() {
        return endGameControlSquare;
    }

    public void setEndGameControlSquare(Boolean endGameControlSquare) {
        this.endGameControlSquare = endGameControlSquare;
    }

    public String getDrivingSystem() {
        return drivingSystem;
    }

    public void setDrivingSystem(String drivingSystem) {
        this.drivingSystem = drivingSystem;
    }

    public String getWheelType() {
        return wheelType;
    }

    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    public Boolean getVisionProc() {
        return visionProc;
    }

    public void setVisionProc(Boolean visionProc) {
        this.visionProc = visionProc;
    }

    public String getGeneralStrategy() {
        return generalStrategy;
    }

    public void setGeneralStrategy(String generalStrategy) {
        this.generalStrategy = generalStrategy;
    }

    public String getIssuesPotential() {
        return issuesPotential;
    }

    public void setIssuesPotential(String issuesPotential) {
        this.issuesPotential = issuesPotential;
    }

    public int getAutoGearTries() {
        return autoGearTries;
    }

    public void addAutoGearTries() {
        this.autoGearTries++;
    }

    public int getAutoGearSuccesses() {
        return autoGearSuccesses;
    }

    public void addAutoGearSuccesses() {
        this.autoGearSuccesses++;
    }

    public int getSuccessfulRightAutoGear() {
        return successfulRightAutoGear;
    }

    public void addSuccessfulRightAutoGear() {
        this.successfulRightAutoGear++;
    }

    public int getSuccessfulLeftAutoGear() {
        return successfulLeftAutoGear;
    }

    public void addSuccessfulLeftAutoGear() {
        this.successfulLeftAutoGear++;
    }

    public int getGamesAutoShoot() {
        return gamesAutoShoot;
    }

    public void addGamesAutoShoot() {
        this.gamesAutoShoot++;
    }

    public int gettimesAutoShoot() {
        return timesAutoShoot;
    }

    public void addtimesAutoShoot() {
        this.timesAutoShoot++;
    }

    public int getAutoBaseLinePass() {
        return autoBaseLinePass;
    }

    public void addAutoBaseLinePass() {
        this.autoBaseLinePass++;
    }

    public int getAutoControlSquareTimes() {
        return autoControlSquareTimes;
    }

    public void addAutoControlSquareTimes() {
        this.autoControlSquareTimes++;
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }

    public void addTimesPlayed() {
        this.timesPlayed++;
    }

    public int getAvgStaticGears() {
        return avgStaticGears;
    }

    public void addAvgStaticGears(int gears) {
        this.avgStaticGears = ((avgStaticGears * (this.timesPlayed - 1)) + (gears)) / timesPlayed;
    }

    public int getAvgDynamicGears() {
        return avgDynamicGears;
    }

    public void addAvgDynamicGears(int gears) {
        this.avgDynamicGears = ((avgDynamicGears * (this.timesPlayed - 1)) + (gears)) / timesPlayed;
    }

    public int getGamesShot() {
        return gamesShot;
    }

    public void addGamesShot() {
        this.gamesShot++;
    }

    public int getAvgSuccesfulShoot() {
        return avgSuccesfulShoot;
    }

    public void addAvgSuccesfulShoot(int shots) {
        this.avgSuccesfulShoot = ((avgSuccesfulShoot * (this.gamesShot - 1)) + (shots)) / gamesShot;
    }

    public int getTimesWonMidSquare() {
        return timesWonMidSquare;
    }

    public void addTimesWonMidSquare() {
        this.timesWonMidSquare++;
    }

    public int getEndGameControlSquareTimes() {
        return endGameControlSquareTimes;
    }

    public void addEndGameControlSquareTimes() {
        this.endGameControlSquareTimes++;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public void addSpeed(int speed) {
        this.avgSpeed = ((avgSpeed * (this.timesPlayed - 1)) + (speed)) / timesPlayed;
    }

    public int getDefenceTimes() {
        return defenceTimes;
    }

    public void addDefenceTimes() {
        this.defenceTimes++;
    }

    public int getTimesCrashed() {
        return timesCrashed;
    }

    public void addTimesCrashed() {
        this.timesCrashed++;
    }

    public String getComments(int i) {
        if (i >= comments.size())
            i = comments.size() - 1;
        return comments.get(i).toString();
    }

    public void addComments(String comment) {
        this.comments.add(comment);
    }
}
