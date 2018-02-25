package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Button autoBtn;
    private EditText gameNum;
    private EditText teamNum;
    private Spinner positionSpinner;
    private String[] scoutingArr;

    private ArrayAdapter adapter;
    private ArrayList<String> positionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        autoBtn = (Button) findViewById(R.id.autoBtn);
        gameNum = (EditText) findViewById(R.id.gameNum);
        teamNum = (EditText) findViewById(R.id.teamNum);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");


        positionList = new ArrayList<>();
        positionList.add("ימין");
        positionList.add("מרכז");
        positionList.add("שמאל");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, positionList);
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        positionSpinner.setAdapter(adapter);

        autoBtn.setOnClickListener(this);

        if (getIntent().getStringArrayExtra("scoutingArr")[1] != null) {
            int num = Integer.parseInt((getIntent().getStringArrayExtra("scoutingArr")[1]));
            num++;
            gameNum.setText("" + num);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == autoBtn.getId()) {

            String teamNumber = teamNum.getText().toString();

            scoutingArr[2] = gameNum.getText().toString();//adds scouted game number

            while (teamNumber.length()<4) {
                teamNumber = 0 + teamNumber;
            }
            scoutingArr[1] = teamNumber;//adds scouted team number
            scoutingArr[3] = positionSpinner.getSelectedItem().toString();//adds scouted team starting position

            Intent intent = new Intent(GameActivity.this, AutonomousActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }

    }
}
