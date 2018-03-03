package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ResultsActivity extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener {
    TextView titleTV;
    ListView resultListView;
    ArrayList<String> viewList;
    ArrayAdapter adapter;
    ArrayList<Team> teams;
    String viewLevel;
    Button backBtn;

    String team;
    int teamPos;
    String type;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        resultListView = findViewById(R.id.resultListView);
        resultListView.setOnItemClickListener(this);
        type = getIntent().getStringExtra("type");
        viewLevel = type;
        titleTV = findViewById(R.id.resultTV);
        titleTV.setText(type);
        team = getIntent().getStringExtra("team");
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);
        readData();
        if(team.equals("")) {
            gameTeamNull();
        }


    }

    private void gameTeamNonNull(){
        viewLevel = type;
        if(!team.equals("")) {
            boolean hasTeam = false;
            for (int i=0;i<teams.size();i++) {
                if(teams.get(i).getTeamNumber().equals(team)) {
                    teamPos = i;
                    hasTeam = true;
                    break;
                }
            }
            if(hasTeam) {
                titleTV.setText(teams.get(teamPos).getTeamNumber()+" - "+teams.get(teamPos).getTeamName());
                viewList = new ArrayList<>();
                viewList.add("אוטונומי");
                viewList.add("שאר המשחק");
                adapter = new ArrayAdapter(ResultsActivity.this,android.R.layout.simple_list_item_1,viewList);
                resultListView.setAdapter(adapter);
            }
            else {
                viewList = new ArrayList<>();
                viewList.add("אין קבוצה כזאת");
                adapter = new ArrayAdapter(ResultsActivity.this,android.R.layout.simple_list_item_1,viewList);
                resultListView.setAdapter(adapter);
                Log.d("teams",teams.size()+" size");
            }
        }
    }

    private void gameTeamNull(){
        viewLevel = type;
        viewList = new ArrayList<>();
        viewList.add("סקייל");
        viewList.add("סוויץ'");
        viewList.add("אקסצ'יינג'");
        viewList.add("תלייה");
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);
    }

    private void gameAvgClimb() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t:teams) {
            if (t.getClimbs().equals("true")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--)
        {
            for (int j = 1; j <= i; j++)
            {
                if (tempTeams.get(j-1).getAvgClimb() < tempTeams.get(j).getAvgClimb())
                {
                    tempTeams.add(j-1,tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t:tempTeams) {
            viewList.add(t.getTeamNumber()+" - "+(t.getAvgClimb()*100)+"% - "+t.getTimesClimbed());
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }

    private void gameAvgScale() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t:teams) {
            if (t.getCubesAt().contains("סקייל")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--)
        {
            for (int j = 1; j <= i; j++)
            {
                if (tempTeams.get(j-1).getAvgScale() < tempTeams.get(j).getAvgScale())
                {
                    tempTeams.add(j-1,tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t:tempTeams) {
            viewList.add(t.getTeamNumber()+" - "+(t.getAvgScale())+" - "+t.getCubesScale());
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }

    private void gameAvgSwitch() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t:teams) {
            if (t.getCubesAt().contains("סוויץ'")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--)
        {
            for (int j = 1; j <= i; j++)
            {
                if (tempTeams.get(j-1).getAvgSwitch() < tempTeams.get(j).getAvgSwitch())
                {
                    tempTeams.add(j-1,tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t:tempTeams) {
            viewList.add(t.getTeamNumber()+" - "+(t.getAvgSwitch())+" - "+t.getCubesSwitch());
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }

    private void gameAvgVault() {
        ArrayList<Team> tempTeams = new ArrayList();
        for (Team t:teams) {
            if (t.getCubesAt().contains("אקסציינג'")) {
                tempTeams.add(t);
            }


        }

        for (int i = (tempTeams.size() - 1); i >= 0; i--)
        {
            for (int j = 1; j <= i; j++)
            {
                if (tempTeams.get(j-1).getAvgVault() < tempTeams.get(j).getAvgVault())
                {
                    tempTeams.add(j-1,tempTeams.remove(j));
                }
            }
        }

        viewList = new ArrayList<>();
        for (Team t:tempTeams) {
            viewList.add(t.getTeamNumber()+" - "+(t.getAvgVault())+" - "+t.getCubesVault());
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        if(type.equals("game")) {
            if(viewLevel.equals("game")) {
                if (team.equals("")) {
                    viewLevel = "gameAvg";
                    if (i==3) {
                        titleTV.setText("טיפוס");
                        gameAvgClimb();
                    }
                    else if (i==0) {
                        titleTV.setText("סקייל");
                        gameAvgScale();
                    }
                    else if (i == 1) {
                        titleTV.setText("סוויץ'");
                        gameAvgSwitch();
                    }
                    else if (i == 2) {
                        titleTV.setText("אקסצ'יינג'");
                        gameAvgVault();
                    }
                }
                else {
                    if (i==0) {
                        viewLevel = "gamesAutoTeam";
                        titleTV.setText(team+" אוטונומי ");
                    }
                    else {
                        viewLevel = "gamesRestTeam";
                        titleTV.setText(" שאר המשחק "+team);
                    }
                    teamGames();
                }
            }
        }
        else if(type.equals("pit")){
            if(viewList.equals("pit")){
                if(team.equals("")){
                    //I'm not sure what to change here
                }


            }
            else if(viewLevel.equals("pitRobot")){
                titleTV.setText(team+" Robot ");
                viewLevel = "pitRobot";
                teamPitRobot();
            }
            else if(viewLevel.equals("pitFunctinalities")){
                titleTV.setText(team+" Robot ");
                viewLevel = "pitFunctinalities";
                teamPitFunctionality();
            }
            else if(viewLevel.equals("PitAutonomus")){
                titleTV.setText(team+" Robot ");
                viewLevel = "pitAutonomus";
                teamPitAutonomus();
            }
        }


    }


    private void teamPitRobot() {
        viewList = new ArrayList<>();
        viewList.add("General Strat: " + teams.get(teamPos).getRobotRole());
        viewList.add("General Strat Comment: " + teams.get(teamPos).getRoleComment());
        viewList.add("Driving System"  + teams.get(teamPos).getDrivingSystem());
        viewList.add("Wheel Type: " + teams.get(teamPos).getWheelType());
        viewList.add("Robots Weight: " + teams.get(teamPos).getRobotMass());
        viewList.add("Potential for Problems: " + teams.get(teamPos).getIssuesPotential());
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }

    private void teamPitFunctionality() {
        viewList = new ArrayList<>();
        viewList.add("Cubes System: " + teams.get(teamPos).getCubeSystem());
        viewList.add("Does Cubes to: " + teams.get(teamPos).getCubesAt());
        viewList.add("Does Climb: " + teams.get(teamPos).getClimbs());
        viewList.add("Can we Climb on: " + teams.get(teamPos).getHelpsClimb());
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);
    }

    private void teamPitAutonomus(){
        viewList = new ArrayList<>();
        viewList.add("Does Pass Auto Line: " + teams.get(teamPos).getAutoBaseLine());
        viewList.add("Does Put Cube In Switch while Auto: " + teams.get(teamPos).getAutoSwitch());
        viewList.add("Does put Cube In Scale whule Auto: " + teams.get(teamPos).getAutoScaleCubes());
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);

    }


    private void teamGames() {
        viewList = teams.get(teamPos).getGames();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,viewList);
        resultListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(backBtn.getId()==view.getId()) {
            switch (viewLevel) {
                case "game":Intent intent = new Intent(this,ScoutingChooseActivity.class);
                    intent.putExtras(getIntent().getExtras());
                    startActivity(intent);
                    finish();
                    break;
                case "gameAvg": gameTeamNull();
                    break;
                case "gamesAutoTeam": gameTeamNonNull();
                    break;
                case "gamesRestTeam": gameTeamNonNull();
                    break;
            }

        }
    }

    public void readData() {
        teams = new ArrayList<>();
        database.collection("teams").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                Log.d("teams","team"+documentSnapshots.size());
                for(DocumentSnapshot doc :documentSnapshots.getDocuments()) {
                    Team t = new Team(doc.get("number").toString(),doc.get("name").toString());
                    t.setAutoBaseLine(doc.get("auto_line").toString());
                    t.setAutoScale(doc.get("auto_scale").toString());
                    t.setAutoSwitch(doc.get("auto_switch").toString());
                    t.setClimbs(doc.get("does_climb").toString());
                    t.setCubesAt(doc.get("cubes_at").toString());
                    t.setCubeSystem(doc.get("cube_system").toString());
                    t.setDrivingSystem(doc.get("driving_system").toString());
                    t.setGeneralStrategy(doc.get("strategy").toString());
                    t.setHelpsClimb(doc.get("helps_climb").toString());
                    t.setIssuesPotential(doc.get("problems").toString());
//            t.setPitScouter(doc.get("scouter").toString());
                    t.setRobotRole(doc.get("role").toString());
                    t.setRoleComment(doc.get("role_comment").toString());
                    t.setWheelType(doc.get("wheel_type").toString());
                    teams.add(t);
                }
                gameTeamNonNull();

            }
        });
        database.collection("games").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                Log.d("teams","game");
                for(DocumentSnapshot doc :documentSnapshots.getDocuments()) {
                    for (int i = 0; i<teams.size();i++) {
                        if (teams.get(i).getTeamNumber().equals(doc.get("team"))) {
                            teams.get(i).addGame(doc.get("game_number").toString());
                            teams.get(i).addAutoLinePass(doc.get("passed_auto_line").toString());
                            teams.get(i).addAutoScaleCubes(doc.get("auto_scale").toString());
                            teams.get(i).addAutoSwitchCubes(doc.get("auto_switch").toString());
                            teams.get(i).addClimbedFast(doc.get("climbed_fast").toString());
                            teams.get(i).addCrashed(doc.get("did_crash").toString());
                            teams.get(i).addDidClimb(doc.get("did_climb").toString());
                            teams.get(i).addGameComments(doc.get("comments").toString());
                            teams.get(i).addGameRole(doc.get("role").toString());
                            teams.get(i).addHelpedClimb(doc.get("helped_climb").toString());
                            teams.get(i).addPickFeeder(doc.get("pick_feeder").toString());
                            teams.get(i).addPickFloor(doc.get("pick_floor").toString());
                            teams.get(i).addPutScale(doc.get("put_scale").toString());
                            teams.get(i).addPutSwitch(doc.get("put_switch").toString());
                            teams.get(i).addPutVault(doc.get("put_vault").toString());
                            teams.get(i).addReachPlatform(doc.get("reach_platform").toString());
                            teams.get(i).addScouter(doc.get("scouter").toString());
                            teams.get(i).addStartPos(doc.get("start_position").toString());
                            break;
                        }
                    }
                }
            }
        });


        database.collection("comments").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                Log.d("teams","comment");
                for(DocumentSnapshot doc :documentSnapshots.getDocuments()) {
                    for (int i = 0; i<teams.size();i++) {
                        if (teams.get(i).getTeamNumber().equals(doc.get("team"))) {
                            teams.get(i).addComments(doc.get("comment").toString());
                            break;
                        }
                    }
                }
            }
        });


    }
}
