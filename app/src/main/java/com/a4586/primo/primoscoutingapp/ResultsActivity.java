package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        if(type.equals("game")) {
            if(viewLevel.equals("game")) {
                if (team.equals("")) {
                    if (i==3) {
                        gameAvgClimb();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(backBtn.getId()==view.getId()) {
            if (viewLevel.equals(type)) {
                Intent intent = new Intent(this,ScoutingChooseActivity.class);
                intent.putExtras(intent.getExtras());
                startActivity(intent);
                finish();
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
