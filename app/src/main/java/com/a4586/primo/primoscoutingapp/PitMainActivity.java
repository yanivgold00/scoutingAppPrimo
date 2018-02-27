package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;


public class PitMainActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private Spinner roleSpinner;
    private EditText roleET;
    private CheckBox vaultCB, switchCB, scaleCB;
    private EditText cubeSystemET;
    private Switch climbsSwitch;
    private EditText helpsClimbET;
    private Switch baseLineSwitch;
    private Button contBtn;

    private ArrayAdapter adapter;
    private ArrayList<String> roleList;
    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_main);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        roleList = new ArrayList<>();
        roleList.add("התקפה");
        roleList.add("הגנה");
        roleList.add("שניהם");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        roleSpinner.setAdapter(adapter);

        roleET = (EditText) findViewById(R.id.commentRoleET);

        vaultCB = findViewById(R.id.vaultCB);
        switchCB = findViewById(R.id.switchCB);
        scaleCB = findViewById(R.id.scaleCB);

        cubeSystemET = findViewById(R.id.cubeSystemET);

        climbsSwitch = findViewById(R.id.climbsSwitch);

        helpsClimbET = findViewById(R.id.helpsClimbET);

        baseLineSwitch = (Switch) findViewById(R.id.baseLineSwitch);

        contBtn = (Button) findViewById(R.id.contBtn);

        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == contBtn.getId()) {
            scoutingArr[3] = roleSpinner.getSelectedItem().toString();
            scoutingArr[4] = roleET.getText().toString();
            scoutingArr[5] = "";
            if(vaultCB.isChecked()){
                scoutingArr[5]+="אקסציינג', ";
            }
            if(switchCB.isChecked()){
                scoutingArr[5]+="סוויץ', ";
            }
            if(scaleCB.isChecked()){
                scoutingArr[5]+="סקייל, ";
            }

            scoutingArr[6] = cubeSystemET.getText().toString();

            scoutingArr[7] = climbsSwitch.isChecked()+"";

            scoutingArr[8] = helpsClimbET.getText().toString();

            scoutingArr[9] = baseLineSwitch.isChecked()+"";

            Intent intent = new Intent(PitMainActivity.this, Pit2MainActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }
    }
}
