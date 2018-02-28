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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ResultsActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    ListView lv;
    String[] arr;
    ArrayAdapter adapter;
    ArrayList<String> teams;
    boolean isInTeams;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        isInTeams = false;
//        arr = new String[3];
//        arr[0] = "0";
//        arr[1] = "1";
//        arr[2] = "2";
//        lv = (ListView) findViewById(R.id.resultListView);
//        lv.setOnItemClickListener(this);
////        myRef.addValueEventListener(this);
//        teams = new ArrayList<>();
//        db.collection("teams")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            //for (DocumentSnapshot document : task.getResult()) {
//                            DocumentSnapshot document = task.getResult().getDocuments().get(2);
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                            for (DocumentSnapshot doc : task.getResult()) {
//                                teams.add(doc.getId());
//                            }
//                            isInTeams = true;
//                            adapter = new ArrayAdapter(ResultsActivity.this, android.R.layout.simple_list_item_1, teams);
//                            lv.setAdapter(adapter);
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final ArrayList<String> teamInfo = new ArrayList<>();
        db.collection("teams")
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
