package com.primo.primoscoutingtest;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.javalite.http.Http;
import org.javalite.http.Post;

public class Pit2MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText drivingSystemET;
    private EditText wheelTypeET;
    private Switch visionSwitch;
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
        visionSwitch = (Switch) findViewById(R.id.visionSwitch);
        stratET = (EditText) findViewById(R.id.stratET);
        issueET = (EditText) findViewById(R.id.issueET);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == sendBtn.getId()) {
            scoutingArr[14] = drivingSystemET.getText().toString();
            scoutingArr[15] = wheelTypeET.getText().toString();
            if (visionSwitch.isChecked()) {
                scoutingArr[16] = "TRUE";
            } else {
                scoutingArr[16] = "TRUE";
            }
            scoutingArr[17] = stratET.getText().toString();
            scoutingArr[18] = issueET.getText().toString();

           SendForm();
        }
    }


    private void SendForm() {
        try {


            String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLScrDeMHTLM0-kliGBVETaw2xaTiEajX7SPAVX_hpkf-9F0O4g/formResponse";


            Log.i("Pit2MainActivity", "a");
            Post post = Http.post(formUrl)
                    .param("entry.152796453", scoutingArr[0])// sends name
                    .param("entry.1816721099", scoutingArr[1])// sends team number
                    .param("entry.107010567", scoutingArr[2])// sends team name
                    .param("entry.962080235", scoutingArr[3])// sends the driving system
                    .param("entry.556400298", scoutingArr[4])// sends the robot's wheel type
                    .param("entry.896162751", scoutingArr[5])// sends the robot's role
                    .param("entry.1032822360", scoutingArr[6])// sends the robot's role comment
                    .param("entry.1862553258", scoutingArr[7])// sends if does dinamic gear
                    .param("entry.88312064", scoutingArr[8])// sends if does static gear
                    .param("entry.1334275440", scoutingArr[9])// sends if crosses auto line
                    .param("entry.1589277901", scoutingArr[10])// sends if shoots
                    .param("entry.303197516", scoutingArr[11])// sends if shoots in auto
                    .param("entry.987288446", scoutingArr[12])// sends if put gears in auto
                    .param("entry.1695539192", scoutingArr[13])// sends where put gears in auto
                    .param("entry.1387442404", scoutingArr[14])// sends if controlls the control square in auto
                    .param("entry.82727232", scoutingArr[15])// sends if controlls the control square in end game
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
        }
    }
}
