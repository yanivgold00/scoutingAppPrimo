package com.a4586.primo.primoscoutingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ResultsActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView lv;
    String[] arr;
    ArrayAdapter adapter;
    ArrayList<Team> teams;
    boolean isInTeams;

    String type;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        isInTeams = false;
        type = getIntent().getStringExtra("type");
        teams = new ArrayList<>();
        database.collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for(DocumentSnapshot doc:task.getResult().getDocuments()) {
                        teams.add(new Team(doc.get("number").toString(),doc.get("name").toString()));
                        int pos = teams.size();
                        pos--;
                        teams.get(pos).setAutoBaseLine(doc.get("auto_line").toString());
                        teams.get(pos).setAutoScale(doc.get("auto_scale").toString());
                        teams.get(pos).setAutoSwitch(doc.get("auto_switch").toString());
                        teams.get(pos).setClimbs(doc.get("does_climb").toString());
                        teams.get(pos).setCubesAt(doc.get("cubes_at").toString());
                        teams.get(pos).setCubeSystem(doc.get("cube_system").toString());
                        teams.get(pos).setDrivingSystem(doc.get("driving_system").toString());
                        teams.get(pos).setGeneralStrategy(doc.get("strategy").toString());
                        teams.get(pos).setHelpsClimb(doc.get("helps_climb").toString());
                        teams.get(pos).setIssuesPotential(doc.get("problems").toString());
                        teams.get(pos).setPitScouter(doc.get("scouter").toString());
                        teams.get(pos).setRobotRole(doc.get("role").toString());
                        teams.get(pos).setRoleComment(doc.get("role_comment").toString());
                        teams.get(pos).setWheelType(doc.get("wheel_type").toString());
                    }
                }
            }
        });
        database.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){

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
            }
        });

        database.collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        for (int i = 0; i<teams.size();i++) {
                            if (teams.get(i).getTeamNumber().equals(doc.get("team"))) {
                                teams.get(i).addComments(doc.get("comment").toString());
                                break;
                            }
                        }
                    }
                }
            }
        });

                    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final ArrayList<String> teamInfo = new ArrayList<>();
        database.collection("teams")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (isInTeams) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(i);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                teamInfo.add("name: " + document.get("name"));
                                teamInfo.add("number: " + document.get("number"));
                                teamInfo.add("role: " + document.get("role"));
                                teamInfo.add("role comment: " + document.get("role_comment"));
                                teamInfo.add("does dinamyc gear: " + document.get("dinami_gear"));
                                teamInfo.add("does static gear: " + document.get("stati_gear"));
                                teamInfo.add("passes base line in auto: " + document.get("base_line"));
                                teamInfo.add("does shoot: " + document.get("shoots"));
                                teamInfo.add("does shoot in auto: " + document.get("auto_shoot"));
                                teamInfo.add("does put gear in auto: " + document.get("auto_gear"));
                                teamInfo.add("where puts gear in auto: " + document.get("where_auto_gear"));
                                teamInfo.add("goes to control square in auto: " + document.get("auto_cont_square"));
                                teamInfo.add("goes to control squarein end game: " + document.get("end_cont_square"));
                                teamInfo.add("what driving system the robot has: " + document.get("driving_system"));
                                teamInfo.add("what is the robot wheel type: " + document.get("wheel_type"));
                                teamInfo.add("does the robot have vision processing: " + document.get("vision"));
                                teamInfo.add("what is the team strategy: " + document.get("strategy"));
                                teamInfo.add("what are some potential problems: " + document.get("problems"));
                                isInTeams = false;
                                adapter = new ArrayAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, teamInfo);
                                lv.setAdapter(adapter);
                            } else {

                                Log.d(TAG, "in teams");
                                isInTeams = true;
                                adapter = new ArrayAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, teams);
                                lv.setAdapter(adapter);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
