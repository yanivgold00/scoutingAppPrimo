package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.javalite.http.Http;
import org.javalite.http.Post;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import static android.content.ContentValues.TAG;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {
    ToggleButton roleBtn;
    Switch didCrashSwitch;
    EditText commentsET;
    Button sendBtn;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String[] scoutingArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_send);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        roleBtn = findViewById(R.id.offenseDefenseBtn);
        roleBtn.setTextOff("התקפה");
        roleBtn.setTextOn("הגנה");
        didCrashSwitch = findViewById(R.id.didCrashSwitch);
        commentsET = findViewById(R.id.commentsET);
        sendBtn = findViewById(R.id.sendBtn);


        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == sendBtn.getId()) {
            scoutingArr[16] = roleBtn.getText().toString();
            scoutingArr[17] = didCrashSwitch.isChecked() + "";
            scoutingArr[18] = commentsET.getText().toString();

            sendFirebase();

        }

    }

    private void sendFirebase() {

        database.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int counter = 1;
//                    DocumentReference doc = database.collection("games").document(scoutingArr[1]);
                    for (DocumentSnapshot doc:task.getResult().getDocuments()) {
                        if (doc.getId().toString().substring(0,3).contains(scoutingArr[1]))
                        {
                            counter++;
                        }
                    }
                    Map<String, Object> game = new HashMap<>();
                    // general
                    game.put("scouter",scoutingArr[0]);
                    game.put("team",scoutingArr[1]);
                    game.put("game_number",scoutingArr[2]);
                    game.put("start_position",scoutingArr[3]);
                    // auto
                    game.put("passed_auto_line", scoutingArr[4]);
                    game.put("auto_switch", scoutingArr[5]);
                    game.put("auto_scale", scoutingArr[6]);
                    //teleop
                    game.put("pick_feeder", scoutingArr[7]);
                    game.put("pick_floor", scoutingArr[8]);
                    game.put("put_scale", scoutingArr[9]);
                    game.put("put_switch", scoutingArr[10]);
                    game.put("put_vault", scoutingArr[11]);
                    //end game
                    game.put("reach_platform", scoutingArr[12]);
                    game.put("did_climb", scoutingArr[13]);
                    game.put("helped_climb",scoutingArr[14]);
                    game.put("climbed_fast",scoutingArr[15]);
                    //general game
                    game.put("role",scoutingArr[16]);
                    game.put("did_crash",scoutingArr[17]);
                    game.put("comments",scoutingArr[18]);
                    Log.v("TAG", "succes");

                } else {
                    Log.v(TAG, "error");
                }
            }
        });

    }

    private void SendForm() {
        try {


            String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSf8hbbZX6_szgBuMoitXPHltq-2LRedoMYezOuPX4_vLNvSYg/formResponse";

            URL url = new URL(formUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.connect();
            http.getErrorStream();

            Log.i("TAG", "a");
            Post post = Http.post(formUrl)
                    .param("entry.121731091", "microgali0")//sends password
                    .param("entry.1597244100", scoutingArr[0])// sends name
                    .param("entry.265635630", scoutingArr[1])// sends game number
                    .param("entry.363359792", scoutingArr[2])// sends team number
                    .param("entry.16142172", scoutingArr[3])// sends team starting position
                    .param("entry.1600769749", scoutingArr[4])// sends if crossed auto line
                    .param("entry.1977761629", scoutingArr[5])// sends amount put in switch
                    .param("entry.1219347675", scoutingArr[6])// sends amount put in scale
                    .param("entry.178698834", scoutingArr[7])// sends how much got from floor
                    .param("entry.1669736291", scoutingArr[8])// sends how much got from feeder
                    .param("entry.1231018242", scoutingArr[9])// sends how much put in switch
                    .param("entry.977065219", scoutingArr[10])// sends how much put in scale
                    .param("entry.1928014287", scoutingArr[11])// sends how much put in exchange
                    .param("entry.982237502", scoutingArr[12])// sends if got to platform
                    .param("entry.982237502", scoutingArr[13])// sends if climbed
                    .param("entry.642395940", scoutingArr[14]);// sends if used platform of robot
//                    .param("", scoutingArr[15])// sends if climbed
//                    .param("", scoutingArr[16])// sends if climbed
//                    .param("", scoutingArr[17])// sends if climbed
//                    .param("", scoutingArr[18]);// sends if climbed
            Log.i("TAG", "b");

            Log.i("TAG", "c");
            if (post.text() != null)
                Toast.makeText(SendActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SendActivity.this, GameActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        } catch (Exception e) {
            Log.i("TAG", "error => " + e.toString());
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            //nextScreenBtn.setClickable(true);
        }
    }


}
