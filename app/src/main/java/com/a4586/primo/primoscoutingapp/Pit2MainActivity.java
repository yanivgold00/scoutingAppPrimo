package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.javalite.http.Http;
import org.javalite.http.Post;

import java.util.HashMap;
import java.util.Map;

public class Pit2MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CheckBox rightAutoSwitchCB, midAutoSwitchCB, leftAutoSwitchCB;
    private CheckBox rightAutoScaleCB, midAutoScaleCB, leftAutoScaleCB;
    private EditText drivingSystemET;
    private EditText wheelTypeET;
    private EditText stratET;
    private EditText issueET;
    private Button sendBtn;

    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_pit2_main);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        drivingSystemET = (EditText) findViewById(R.id.drivingSystemET);
        wheelTypeET = (EditText) findViewById(R.id.wheelTypeET);
        stratET = (EditText) findViewById(R.id.stratET);
        issueET = (EditText) findViewById(R.id.issueET);
        rightAutoScaleCB = findViewById(R.id.rightAutoScale);
        midAutoScaleCB = findViewById(R.id.midAutoScale);
        leftAutoScaleCB = findViewById(R.id.leftAutoScale);

        rightAutoSwitchCB = findViewById(R.id.rightAutoSwitch);
        midAutoSwitchCB =findViewById(R.id.midAutoSwitch);
        leftAutoSwitchCB = findViewById(R.id.leftAutoSwitch);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setClickable(true);
        sendBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if ((v.getId() == sendBtn.getId())) {
            sendBtn.setClickable(false);
            scoutingArr[10] = "";
            if(rightAutoSwitchCB.isChecked()) {
                scoutingArr[10] += "ימין,";
            }
            if(midAutoSwitchCB.isChecked()) {
                scoutingArr[10] += "מרכז,";
            }
            if(leftAutoSwitchCB.isChecked()) {
                scoutingArr[10] += "שמאל,";
            }

            scoutingArr[11] = "";
            if(rightAutoScaleCB.isChecked()) {
                scoutingArr[11] += "ימין,";
            }
            if(midAutoScaleCB.isChecked()) {
                scoutingArr[11] += "מרכז,";
            }
            if(leftAutoScaleCB.isChecked()) {
                scoutingArr[11] += "שמאל,";
            }
            scoutingArr[12] = drivingSystemET.getText().toString();
            scoutingArr[13] = wheelTypeET.getText().toString();
            scoutingArr[14] = stratET.getText().toString();
            scoutingArr[15] = issueET.getText().toString();

            sendForm();
            Toast.makeText(Pit2MainActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pit2MainActivity.this, PitFormActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }
    }

    private void sendForm() {
        database.collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int counter = 0;
//                    DocumentReference doc = database.collection("games").document(scoutingArr[1]);
                    for (DocumentSnapshot doc:task.getResult().getDocuments()) {
                        counter++;
                    }
                    Map<String, Object> team = new HashMap<>();
                    team.put("scouter",scoutingArr[0]);
                    team.put("number", scoutingArr[1]);
                    team.put("name", scoutingArr[2]);
                    team.put("role", scoutingArr[3]);
                    team.put("role_comment", scoutingArr[4]);
                    team.put("cubes_at", scoutingArr[5]);
                    team.put("cube_system", scoutingArr[6]);
                    team.put("does_climb", scoutingArr[7]);
                    team.put("helps_climb", scoutingArr[8]);
                    team.put("auto_line", scoutingArr[9]);
                    team.put("auto_switch", scoutingArr[10]);
                    team.put("auto_scale", scoutingArr[11]);
                    team.put("driving_system", scoutingArr[12]);
                    team.put("wheel_type", scoutingArr[13]);
                    team.put("strategy", scoutingArr[14]);
                    team.put("problems", scoutingArr[15]);

                    database.collection("teams").document(""+counter).set(team);
                }
            }
        });


    }

    private void SendForm() {
        try {


            String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLScrDeMHTLM0-kliGBVETaw2xaTiEajX7SPAVX_hpkf-9F0O4g/formResponse";


            Log.i("Pit2MainActivity", "a");
            Post post = Http.post(formUrl)
                    .param("entry.152796453", scoutingArr[0])// sends name
                    .param("entry.1816721099", scoutingArr[1])// sends team number
                    .param("entry.107010567", scoutingArr[2])// sends team name
                    .param("entry.896162751", scoutingArr[3])// sends the team role
                    .param("entry.1032822360", scoutingArr[4])// sends the role comment
                    .param("entry.1862553258", scoutingArr[5])// sends if does dynamic gear
                    .param("entry.88312064", scoutingArr[6])// sends if does static gear
                    .param("entry.1334275440", scoutingArr[7])// sends if passed auto line
                    .param("entry.1589277901", scoutingArr[8])// sends if shoots
                    .param("entry.303197516", scoutingArr[9])// sends if auto shoots
                    .param("entry.987288446", scoutingArr[10])// sends if puts auto gear
                    .param("entry.1695539192", scoutingArr[11])// sends where puts auto gear
                    .param("entry.1387442404", scoutingArr[12])// sends if goes to control square in auto
                    .param("entry.82727232", scoutingArr[13])// sends if goes to control square at end game
                    .param("entry.962080235", scoutingArr[14])// sends the driving system
                    .param("entry.556400298", scoutingArr[15])// sends the wheel type
                    .param("entry.1173387920", scoutingArr[16])// sends if does vision processing
                    .param("entry.1906800055", scoutingArr[17])// sends general strategy
                    .param("entry.2014479588", scoutingArr[18]);// sends potential problems
            Log.i("Pit2MainActivity", "b");

            Log.i("Pit2MainActivity", "c");
            if (post.text() != null)
                Toast.makeText(Pit2MainActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pit2MainActivity.this, PitFormActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        } catch (Exception e) {
            Log.i("Pit2MainActivity", e.getStackTrace().toString());
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            sendBtn.setClickable(true);
        }
    }
}
