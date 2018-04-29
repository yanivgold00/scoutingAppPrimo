package com.a4586.primo.primoscoutingapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 26/11/2017.
 */

public class Team implements Serializable {
    // Basic Info
    private String teamNumber;
    private String teamName;

    // Pit Scouting Info
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
    private String robotMass;
    private String climbs;
    private String helpsClimb;
    private String autoSwitch;
    private String autoScale;


    // Game Scouting Info
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

    // Comments
    private ArrayList<String> comments;

    // Constructor (puts values in team number and main and initializes arrays)
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

    // Method that return the percentage of games where the robot climbed
    public double getAvgClimb() {
        double avg = getTimesClimbed();
        if (avg != 0) {
            avg /= didClimb.size();
        } else {
            avg = 0;
        }
        return avg;
    }

    // Method that returns the average amount of power cubes the robot put in the switch
    public double getAvgSwitch() {
        double avg = getCubesSwitch();
        if (avg != 0) {
            avg /= (putSwitch.size());
        } else {
            avg = 0;
        }
        return avg;
    }

    // Method that returns the average amount of power cubes the robot put in the scale
    public double getAvgScale() {
        double avg = getCubesScale();
        if (avg != 0) {
            avg /= (putScale.size());
        } else {
            avg = 0;
        }
        return avg;
    }

    // Method that returns the average amount of power cubes the robot put in the vault
    public double getAvgVault() {
        double avg = getCubesVault();
        if (avg != 0) {
            avg /= (putVault.size());
        } else {
            avg = 0;
        }
        return avg;
    }

    // Method that returns the amount of times the robot climbed
    public double getTimesClimbed() {
        double counter = 0;
        for (String climb : this.didClimb) {
            if (climb.equals("כן") || climb.equals("נעזר במוט מוארך של רובוט אחר") || climb.equals("נעזר בפלטפורמה של רובוט אחר")) {
                counter++;
            }
        }
        return counter;
    }

    // Method that returns the total amount of power cubes the robot put on the scale
    public double getCubesScale() {
        double counter = 0;
        for (String cubes : this.putScale) {
            counter += Double.parseDouble(cubes);
        }

        for (String cubes : this.autoScaleCubes) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    // Method that returns the total amount of power cubes the robot put on the switch
    public double getCubesSwitch() {
        double counter = 0;
        for (String cubes : this.putSwitch) {
            counter += Double.parseDouble(cubes);
        }

        for (String cubes : this.autoSwitchCubes) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    // Method that returns the total amount of power cubes the robot put in the vault
    public double getCubesVault() {
        double counter = 0;
        for (String cubes : this.putVault) {
            counter += Double.parseDouble(cubes);
        }

        return counter;
    }

    // Returns the scouter of the pit form
    public String getPitScouter() {
        return pitScouter;
    }

    // Sets the scouter of the pit form
    public void setPitScouter(String pitScouter) {
        this.pitScouter = pitScouter;
    }

    // Returns string that contains where the robot can put put power cubes
    public String getCubesAt() {
        return cubesAt;
    }

    // Sets string that contains where the robot can put put power cubes
    public void setCubesAt(String cubesAt) {
        this.cubesAt = cubesAt;
    }

    // Returns the system the robot uses for catching and transporting power cubes
    public String getCubeSystem() {
        return cubeSystem;
    }

    // Sets the system the robot uses for catching and transporting power cubes
    public void setCubeSystem(String cubeSystem) {
        this.cubeSystem = cubeSystem;
    }

    // Returns whether climbs or not
    public String getClimbs() {
        return climbs;
    }

    // Sets whether climbs or not
    public void setClimbs(String climbs) {
        this.climbs = climbs;
    }

    // Returns whether helps another robot to climb
    public String getHelpsClimb() {
        return helpsClimb;
    }

    // Sets whether helps another robot to climb
    public void setHelpsClimb(String helpsClimb) {
        this.helpsClimb = helpsClimb;
    }

    // Returns whether the robot is able to put in switch in the autonomous period or not
    public String getAutoSwitch() {
        return autoSwitch;
    }

    // Sets whether the robot is able to put in switch in the autonomous period or not
    public void setAutoSwitch(String autoSwitch) {
        this.autoSwitch = autoSwitch;
    }

    // Returns whether the robot is able to put in scale in the autonomous period or not
    public String getAutoScale() {
        return autoScale;
    }

    // Sets whether the robot is able to put in scale in the autonomous period or not
    public void setAutoScale(String autoScale) {
        this.autoScale = autoScale;
    }

    // Returns a list of the games the robot participated in
    public ArrayList<String> getGames() {
        return games;
    }

    // Adds a game the robot participated in
    public void addGame(String game) {
        this.games.add(game);
    }

    // Returns a list of the scouters of the robot in the games the robot participated in
    public ArrayList<String> getScouter() {
        return scouter;
    }

    // Adds a scouter to the scouters list
    public void addScouter(String scouter) {
        this.scouter.add(scouter);
    }

    // Returns a list of the robot starting position in is games
    public ArrayList<String> getStartPos() {
        return startPos;
    }

    // Adds a starting position to the starting position list
    public void addStartPos(String startPos) {
        this.startPos.add(startPos);
    }

    // Returns a list of whether passed auto line at any game
    public ArrayList<String> getAutoLinePass() {
        return autoLinePass;
    }

    // Adds whether passed auto line to the list of whether passes auto line
    public void addAutoLinePass(String autoLinePass) {
        this.autoLinePass.add(autoLinePass);
    }

    // Returns a list of the amount of power cubes the robot put in switch at the auto period
    public ArrayList<String> getAutoSwitchCubes() {
        return autoSwitchCubes;
    }

    // Adds amount of power cubes the robot put in switch at the auto period
    public void addAutoSwitchCubes(String autoSwitchCubes) {
        this.autoSwitchCubes.add(autoSwitchCubes);
    }

    // Returns a list of the amount of power cubes the robot put in scale at the auto period
    public ArrayList<String> getAutoScaleCubes() {
        return autoScaleCubes;
    }

    // Adds amount of power cubes the robot put in scale at the auto period
    public void addAutoScaleCubes(String autoScaleCubes) {
        this.autoScaleCubes.add(autoScaleCubes);
    }

    // Returns a list of the amount of power cubes the robot picked from feeder
    public ArrayList<String> getPickFeeder() {
        return pickFeeder;
    }

    // Adds amount of power cubes the robot picked from feeder
    public void addPickFeeder(String pickFeeder) {
        this.pickFeeder.add(pickFeeder);
    }

    // Returns a list of the amount of power cubes the robot picked from floor
    public ArrayList<String> getPickFloor() {
        return pickFloor;
    }

    // Adds amount of power cubes the robot picked from floor
    public void addPickFloor(String pickFloor) {
        this.pickFloor.add(pickFloor);
    }

    // Returns a list of the amount of power cubes the robot put in vault
    public ArrayList<String> getPutVault() {
        return putVault;
    }

    // Adds amount of power cubes the robot put in vault
    public void addPutVault(String putVault) {
        this.putVault.add(putVault);
    }

    // Returns a list of the amount of power cubes the robot put in switch
    public ArrayList<String> getPutSwitch() {
        return putSwitch;
    }

    // Adds amount of power cubes the robot put in switch
    public void addPutSwitch(String putSwitch) {
        this.putSwitch.add(putSwitch);
    }

    // Returns a list of the amount of power cubes the robot put in scale
    public ArrayList<String> getPutScale() {
        return putScale;
    }

    // Adds amount of power cubes the robot put in scale
    public void addPutScale(String putScale) {
        this.putScale.add(putScale);
    }

    // Returns a list of whether reached the platform or not
    public ArrayList<String> getReachPlatform() {
        return reachPlatform;
    }

    // Adds whether reached the platform or not
    public void addReachPlatform(String reachPlatform) {
        this.reachPlatform.add(reachPlatform);
    }

    // Returns a list of whether climbed or not
    public ArrayList<String> getDidClimb() {
        return didClimb;
    }

    // Adds whether climbed or not
    public void addDidClimb(String didClimb) {
        this.didClimb.add(didClimb);
    }

    // Returns a list of whether helped climb or not
    public ArrayList<String> getHelpedClimb() {
        return helpedClimb;
    }

    // Adds whether helped climbed or not
    public void addHelpedClimb(String helpedClimb) {
        this.helpedClimb.add(helpedClimb);
    }

    // Returns a list of whether climbed fast or not
    public ArrayList<String> getClimbedFast() {
        return climbedFast;
    }

    // Adds whether climbed fast or not
    public void addClimbedFast(String climbedFast) {
        this.climbedFast.add(climbedFast);
    }

    // Returns List the robots role in the game
    public ArrayList<String> getGameRole() {
        return gameRole;
    }

    // Adds the robots role
    public void addGameRole(String gameRole) {
        this.gameRole.add(gameRole);
    }

    // Returns a list of whether crashed or not
    public ArrayList<String> getCrashed() {
        return crashed;
    }

    // Adds whether crashed or not
    public void addCrashed(String crashed) {
        this.crashed.add(crashed);
    }

    // Returns comments from games
    public ArrayList<String> getGameComments() {
        return gameComments;
    }

    // Adds comment from game
    public void addGameComments(String gameComments) {
        this.gameComments.add(gameComments);
    }

    // Returns non game comments
    public ArrayList<String> getComments() {
        return comments;
    }

    // Adds non game comment
    public void addComments(String comments) {
        this.comments.add(comments);
    }

    // Returns team number
    public String getTeamNumber() {
        return teamNumber;
    }

    // Returns team name
    public String getTeamName() {
        return teamName;
    }

    // Returns the robot role
    public String getRobotRole() {
        return robotRole;
    }

    // Sets Robot role
    public void setRobotRole(String robotRole) {
        this.robotRole = robotRole;
    }

    // Returns the robot role comment
    public String getRoleComment() {
        return roleComment;
    }

    // Sets the robot role comment
    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }

    // Returns whether passes base line in the auto period
    public String getAutoBaseLine() {
        return autoBaseLine;
    }

    // Sets whether passes base line in the auto period
    public void setAutoBaseLine(String autoBaseLine) {
        this.autoBaseLine = autoBaseLine;
    }

    // Returns the Driving system
    public String getDrivingSystem() {
        return drivingSystem;
    }

    // Sets the driving system
    public void setDrivingSystem(String drivingSystem) {
        this.drivingSystem = drivingSystem;
    }

    // Returns wheel type
    public String getWheelType() {
        return wheelType;
    }

    // Sets Wheel type
    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    // Returns general strategy
    public String getGeneralStrategy() {
        return generalStrategy;
    }

    // Sets general strategy
    public void setGeneralStrategy(String generalStrategy) {
        this.generalStrategy = generalStrategy;
    }

    // Returns potential issues
    public String getIssuesPotential() {
        return issuesPotential;
    }

    // Sets potential issues
    public void setIssuesPotential(String issuesPotential) {
        this.issuesPotential = issuesPotential;
    }

    // Sets the robot mass
    public void setRobotMass(String robotMass) {
        this.robotMass = robotMass;
    }

    // Returns the robot mass
    public String getRobotMass() {
        return this.robotMass;
    }


}
