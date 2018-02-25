package com.primo.primoscoutingtest;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class TeleopActivity extends AppCompatActivity  implements View.OnClickListener, Serializable{
    private EditText statiGearNum;
    private EditText dinamiGearNum;
    private Switch didShot;
    private EditText fuel;
    private Button endGameBtn;
    private String[] scoutingArr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);
        statiGearNum=(EditText)findViewById(R.id.staticGearNum);
        dinamiGearNum=(EditText)findViewById(R.id.dinamiGearNum);
        didShot=(Switch)findViewById(R.id.didShoot);
        fuel=(EditText)findViewById(R.id.fuel);
        endGameBtn=(Button)findViewById(R.id.endGameBtn);

        scoutingArr=getIntent().getStringArrayExtra("scoutingArr");



        endGameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==endGameBtn.getId()) {
            Toast.makeText(TeleopActivity.this, "teleop sent!", Toast.LENGTH_SHORT).show();

            if (statiGearNum.getText().toString().equals("")) {
                scoutingArr[11]="0";// adds zero if nothing is written
            } else {
                scoutingArr[11]=statiGearNum.getText().toString();// adds amount of gears put on the static peg
            }

            if (dinamiGearNum.getText().toString().equals("")) {
                scoutingArr[12]="0";// adds zero if nothing is written
            } else {
                scoutingArr[12]=dinamiGearNum.getText().toString();// adds amount of gears put on the dinamic peg
            }

            if (didShot.isChecked()) {
                scoutingArr[13]="true";// adds true if robot shot in teleop
            } else {
                scoutingArr[13]="false";// adds false if robot didn't shot in teleop
            }

            if (fuel.getText().toString().equals("")) {
                scoutingArr[14]="0";// adds zero if nothing is written
            } else {
                scoutingArr[14]=fuel.getText().toString();// adds amount of fuel shot in teleop
            }

            Intent intent = new Intent(TeleopActivity.this, EndGameActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }

    }
}
