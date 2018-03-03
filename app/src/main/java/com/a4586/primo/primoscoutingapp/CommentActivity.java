package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText teamET;
    private EditText commentET;
    private Button sendBtn;
    private Button backBtn;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        teamET = findViewById(R.id.teamET);
        commentET = findViewById(R.id.commentsET);
        sendBtn = findViewById(R.id.sendBtn);
        backBtn = findViewById(R.id.backBtn);

        sendBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==sendBtn.getId()) {
            sendFireBase();
            }
        if (view.getId()==backBtn.getId()) {
            Intent intent = new Intent(this,ScoutingChooseActivity.class);
            intent.putExtra("level",getIntent().getStringExtra("level"));
            intent.putExtra("name",getIntent().getStringExtra("name"));
            startActivity(intent);
            finish();
        }
    }

    private void sendFireBase() {
        database.collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int counter = 0;
//                    DocumentReference doc = database.collection("games").document(scoutingArr[1]);
                    String teamNumber = teamET.getText().toString();

                    for (DocumentSnapshot doc:task.getResult().getDocuments()) {
                        counter++;

                    }

                    Map<String, Object> comment = new HashMap<>();
                    comment.put("name",getIntent().getStringExtra("name"));
                    comment.put("team",teamET.getText().toString());
                    comment.put("comment",commentET.getText().toString());

                    database.collection("comments").document(""+counter).set(comment);
                    Log.v("TAG", "succes");
                    Toast.makeText(CommentActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
                    teamET.setText("");
                    commentET.setText("");

                } else {
                    Toast.makeText(CommentActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    Log.v("TAG", "error");

                }
            }
        });
    }
}
