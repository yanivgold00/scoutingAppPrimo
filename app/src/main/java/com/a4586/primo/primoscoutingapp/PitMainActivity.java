package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;


public class PitMainActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private Spinner roleSpinner;
    private EditText roleET;
    private Switch dinamiSwitch;
    private Switch statiSwitch;
    private Switch baseLineSwitch;
    private Switch doesShootSwitch;
    private Switch autoShootSwitch;
    private Switch autoGearSwitch;
    private Spinner autoGearSpinner;
    private Switch autoControlSwitch;
    private Switch endControlSwitch;
    private Button contBtn;

    private ArrayAdapter adapter;
    private ArrayList<String> roleList;
    private ArrayList<String> gearDirList;
    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_main);

//        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
//
//        roleList = new ArrayList<>();
//        roleList.add("התקפה");
//        roleList.add("הגנה");
//        roleList.add("שניהם");
//        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList);
//        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
//        roleSpinner.setAdapter(adapter);
//
//        roleET = (EditText) findViewById(R.id.commentRoleET);
//
//        dinamiSwitch = (Switch) findViewById(R.id.dinamicGearSwitch);
//
//        statiSwitch = (Switch) findViewById(R.id.staticGearSwitch);
//
//        baseLineSwitch = (Switch) findViewById(R.id.baseLineSwitch);
//
//        doesShootSwitch = (Switch) findViewById(R.id.isShootSwitch);
//
//        autoShootSwitch = (Switch) findViewById(R.id.autoShootSwitch);
//
//        autoGearSwitch = (Switch) findViewById(R.id.autoGearSwitch);
//
//        gearDirList = new ArrayList<>();
//        gearDirList.add("לא שם גיר");
//        gearDirList.add("ימין");
//        gearDirList.add("שמאל");
//        gearDirList.add("שניהם");
//        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gearDirList);
//        autoGearSpinner = (Spinner) findViewById(R.id.autoGearSpinner);
//        autoGearSpinner.setAdapter(adapter);
//
//        autoControlSwitch = (Switch) findViewById(R.id.autoContSqSwitch);
//
//        endControlSwitch = (Switch) findViewById(R.id.endContSqSwitch);
//
//        contBtn = (Button) findViewById(R.id.contBtn);
//
//        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == contBtn.getId()) {
            scoutingArr[3] = roleSpinner.getSelectedItem().toString();
            scoutingArr[4] = roleET.getText().toString();
            if (dinamiSwitch.isChecked()) {
                scoutingArr[5] = "TRUE";
            } else {
                scoutingArr[5] = "FALSE";
            }

            if (statiSwitch.isChecked()) {
                scoutingArr[6] = "TRUE";
            } else {
                scoutingArr[6] = "FALSE";
            }

            if (baseLineSwitch.isChecked()) {
                scoutingArr[7] = "TRUE";
            } else {
                scoutingArr[7] = "FALSE";
            }

            if (doesShootSwitch.isChecked()) {
                scoutingArr[8] = "TRUE";
            } else {
                scoutingArr[8] = "FALSE";
            }

            if (autoShootSwitch.isChecked()) {
                scoutingArr[9] = "TRUE";
            } else {
                scoutingArr[9] = "FALSE";
            }

            if (autoGearSwitch.isChecked()) {
                scoutingArr[10] = "TRUE";
            } else {
                scoutingArr[10] = "FALSE";
            }
            scoutingArr[11] = autoGearSpinner.getSelectedItem().toString();
            if (autoControlSwitch.isChecked()) {
                scoutingArr[12] = "TRUE";
            } else {
                scoutingArr[12] = "FALSE";
            }

            if (endControlSwitch.isChecked()) {
                scoutingArr[13] = "TRUE";
            } else {
                scoutingArr[13] = "FALSE";
            }

            Intent intent = new Intent(PitMainActivity.this, Pit2MainActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }
    }
}
