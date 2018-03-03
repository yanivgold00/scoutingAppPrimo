package com.a4586.primo.primoscoutingapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 26/11/2017.
 */

public class Team implements Serializable {
    private String teamNumber;
    private String teamName;

    //pit scouting
    private String pitScouter;
    private String robotRole;
    private String roleComment;
    private String autoBaseLine;
    private String drivingSystem;
    private String wheelType;
    private String generalStrategy;
    private String issuesPotential;
    private String cubesAt;
    private String cubeSystem;
    private String climbs;
    private String helpsClimb;
    private String autoSwitch;
    private String autoScale;




    //game scouting
    private ArrayList<String> games;
    private ArrayList<String> scouter;
    private ArrayList<String> startPos;
    private ArrayList<String> autoLinePass;
    private ArrayList<String> autoSwitchCubes;
    private ArrayList<String> autoScaleCubes;
    private ArrayList<String> pickFeeder;
    private ArrayList<String> pickFloor;
    private ArrayList<String> putVault;
    private ArrayList<String> putSwitch;
    private ArrayList<String> putScale;
    private ArrayList<String> reachPlatform;
    private ArrayList<String> didClimb;
    private ArrayList<String> helpedClimb;
    private ArrayList<String> climbedFast;
    private ArrayList<String> gameRole;
    private ArrayList<String> crashed;
    private ArrayList<String> gameComments;

    //comments
    private ArrayList<String> comments;

    public Team(String teamNumber, String teamName) {
        this.teamNumber = teamNumber;
        this.teamName = teamName;
        this.scouter = new ArrayList<>();
        this.startPos = new ArrayList<>();
        this.autoLinePass = new ArrayList<>();
        this.autoSwitchCubes = new ArrayList<>();
        this.autoScaleCubes = new ArrayList<>();
        this.pickFeeder = new ArrayList<>();
        this.pickFloor = new ArrayList<>();
        this.putVault = new ArrayList<>();
        this.putSwitch = new ArrayList<>();
        this.putScale = new ArrayList<>();
        this.reachPlatform = new ArrayList<>();
        this.didClimb = new ArrayList<>();
        this.helpedClimb = new ArrayList<>();
        this.climbedFast = new ArrayList<>();
        this.gameRole = new ArrayList<>();
        this.crashed = new ArrayList<>();
        this.gameComments = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public double getAvgClimb() {
        double avg = getTimesClimbed();
        if (avg != 0) {
            avg /= didClimb.size();
        }
        else {
            avg = 0;
        }
        return avg;
    }

    public double getTimesClimbed(){
        double counter = 0;
        for (String climb:this.didClimb) {
            if (climb.equals("כן")||climb.equals("נעזר במוט מוארך של רובוט אחר")||climb.equals("נעזר בפלטפורמה של רובוט אחר")) {
                counter++;
            }
        }
        return counter;
    }

    public double getAvgScale() {
        double avg = getCubesScale();
        if (avg != 0) {
            avg /= (putScale.size());
        }
        else {
            avg = 0;
        }
        return avg;
    }

    public double getCubesScale() {
        double counter = 0;
        for (String cubes:this.putScale) {
            counter += Double.parseDouble(cubes);
        }

        for (String cubes:this.autoScaleCubes) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    public double getAvgSwitch() {
        double avg = getCubesSwitch();
        if (avg != 0) {
            avg /= (putSwitch.size());
        }
        else {
            avg = 0;
        }
        return avg;
    }

    public double getCubesSwitch() {
        double counter = 0;
        for (String cubes:this.putSwitch) {
            counter += Double.parseDouble(cubes);
        }

        for (String cubes:this.autoSwitchCubes) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    public double getAvgVault() {
        double avg = getCubesVault();
        if (avg != 0) {
            avg /= (putVault.size());
        }
        else {
            avg = 0;
        }
        return avg;
    }

    public double getCubesVault() {
        double counter = 0;
        for (String cubes:this.putVault) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPitScouter() {
        return pitScouter;
    }

    public void setPitScouter(String pitScouter) {
        this.pitScouter = pitScouter;
    }

    public String getCubesAt() {
        return cubesAt;
    }

    public void setCubesAt(String cubesAt) {
        this.cubesAt = cubesAt;
    }

    public String getCubeSystem() {
        return cubeSystem;
    }

    public void setCubeSystem(String cubeSystem) {
        this.cubeSystem = cubeSystem;
    }

    public String getClimbs() {
        return climbs;
    }

    public void setClimbs(String climbs) {
        this.climbs = climbs;
    }

    public String getHelpsClimb() {
        return helpsClimb;
    }

    public void setHelpsClimb(String helpsClimb) {
        this.helpsClimb = helpsClimb;
    }

    public String getAutoSwitch() {
        return autoSwitch;
    }

    public void setAutoSwitch(String autoSwitch) {
        this.autoSwitch = autoSwitch;
    }

    public String getAutoScale() {
        return autoScale;
    }

    public ArrayList<String> getGames() {
        return games;
    }

    public void addGame(String game) {
        this.games.add(game);
    }

    public void setAutoScale(String autoScale) {
        this.autoScale = autoScale;
    }

    public ArrayList<String> getScouter() {
        return scouter;
    }

    public void addScouter(String scouter) {
        this.scouter.add(scouter);
    }

    public ArrayList<String> getStartPos() {
        return startPos;
    }

    public void addStartPos(String startPos) {
        this.startPos.add(startPos);
    }

    public ArrayList<String> getAutoLinePass() {
        return autoLinePass;
    }

    public void addAutoLinePass(String autoLinePass) {
        this.autoLinePass.add(autoLinePass);
    }

    public ArrayList<String> getAutoSwitchCubes() {
        return autoSwitchCubes;
    }

    public void addAutoSwitchCubes(String autoSwitchCubes) {
        this.autoSwitchCubes.add(autoSwitchCubes);
    }

    public ArrayList<String> getAutoScaleCubes() {
        return autoScaleCubes;
    }

    public void addAutoScaleCubes(String autoScaleCubes) {
        this.autoScaleCubes.add(autoScaleCubes);
    }

    public ArrayList<String> getPickFeeder() {
        return pickFeeder;
    }

    public void addPickFeeder(String pickFeeder) {
        this.pickFeeder.add(pickFeeder);
    }

    public ArrayList<String> getPickFloor() {
        return pickFloor;
    }

    public void addPickFloor(String pickFloor) {
        this.pickFloor.add(pickFloor);
    }

    public ArrayList<String> getPutVault() {
        return putVault;
    }

    public void addPutVault(String putVault) {
        this.putVault.add(putVault);
    }

    public ArrayList<String> getPutSwitch() {
        return putSwitch;
    }

    public void addPutSwitch(String putSwitch) {
        this.putSwitch.add(putSwitch);
    }

    public ArrayList<String> getPutScale() {
        return putScale;
    }

    public void addPutScale(String putScale) {
        this.putScale.add(putScale);
    }

    public ArrayList<String> getReachPlatform() {
        return reachPlatform;
    }

    public void addReachPlatform(String reachPlatform) {
        this.reachPlatform.add(reachPlatform);
    }

    public ArrayList<String> getDidClimb() {
        return didClimb;
    }

    public void addDidClimb(String didClimb) {
        this.didClimb.add(didClimb);
    }

    public ArrayList<String> getHelpedClimb() {
        return helpedClimb;
    }

    public void addHelpedClimb(String helpedClimb) {
        this.helpedClimb.add(helpedClimb);
    }

    public ArrayList<String> getClimbedFast() {
        return climbedFast;
    }

    public void addClimbedFast(String climbedFast) {
        this.climbedFast.add(climbedFast);
    }

    public ArrayList<String> getGameRole() {
        return gameRole;
    }

    public void addGameRole(String gameRole) {
        this.gameRole.add(gameRole);
    }

    public ArrayList<String> getCrashed() {
        return crashed;
    }

    public void addCrashed(String crashed) {
        this.crashed.add(crashed);
    }

    public ArrayList<String> getGameComments() {
        return gameComments;
    }

    public void addGameComments(String gameComments) {
        this.gameComments.add(gameComments);
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void addComments(String comments) {
        this.comments.add(comments);
    }

    public String getTeamNumber() {
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


    public String getAutoBaseLine() {
        return autoBaseLine;
    }

    public void setAutoBaseLine(String autoBaseLine) {
        this.autoBaseLine = autoBaseLine;
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

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

}
