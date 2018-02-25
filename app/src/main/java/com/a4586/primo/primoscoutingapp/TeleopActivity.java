package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class TeleopActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Spinner cycleTookFromSpinner;
    private Spinner cyclePutInSpinner;
    private Button nextCycleBtn;
    private Button endGameBtn;
    private String[] scoutingArr;
    private ArrayList<String> tookFrom;
    private ArrayList<String> putIn;

    private ArrayAdapter adapter;

    private int tookFeeder, tookFloor, putScale, putSwitch, putVault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);

        cyclePutInSpinner = findViewById(R.id.cyclePutInSpinner);
        cycleTookFromSpinner = findViewById(R.id.cycleTookFromSpinner);
        nextCycleBtn = findViewById(R.id.nextCycleBtn);
        endGameBtn = findViewById(R.id.endGameBtn);

        tookFrom = new ArrayList<>();
        tookFrom.add(" ");
        tookFrom.add("רצפה");
        tookFrom.add("פידר/אקסצ'יינג'");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tookFrom);
        cycleTookFromSpinner.setAdapter(adapter);

        putIn = new ArrayList<>();
        putIn.add(" ");
        putIn.add("סקייל");
        putIn.add("סוויץ'");
        putIn.add("אקסצ'יינג'");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, putIn);
        cyclePutInSpinner.setAdapter(adapter);

        tookFeeder = 0;
        tookFloor = 0;
        putScale = 0;
        putSwitch = 0;
        putVault = 0;

        endGameBtn.setOnClickListener(this);
        nextCycleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == endGameBtn.getId()) {
            if (cycleTookFromSpinner.getSelectedItem().toString().equals("רצפה")) {
                tookFloor++;
            } else if (cycleTookFromSpinner.getSelectedItem().toString().equals("פידר/אקסצ'יינג'")) {
                tookFeeder++;
            }

            if (cyclePutInSpinner.getSelectedItem().toString().equals("סקייל")) {
                putScale++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("סוויץ'")) {
                putSwitch++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("אקסצ'יינג'")) {
                putVault++;
            }
            scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
            scoutingArr[7] = tookFloor + "";
            scoutingArr[8] = tookFeeder + "";
            scoutingArr[9] = putVault + "";
            scoutingArr[10] = putSwitch + "";
            scoutingArr[11] = putScale + "";

            Intent intent = new Intent(TeleopActivity.this, EndGameActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();
        }

        if (v.getId() == nextCycleBtn.getId()) {
            if (cycleTookFromSpinner.getSelectedItem().toString().equals("רצפה")) {
                tookFloor++;
            } else if (cycleTookFromSpinner.getSelectedItem().toString().equals("פידר/אקסצ'יינג'")) {
                tookFeeder++;
            }

            if (cyclePutInSpinner.getSelectedItem().toString().equals("סקייל")) {
                putScale++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("סוויץ'")) {
                putSwitch++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("אקסצ'יינג'")) {
                putVault++;
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tookFrom);
            cycleTookFromSpinner.setAdapter(adapter);

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, putIn);
            cyclePutInSpinner.setAdapter(adapter);
        }

    }
}
