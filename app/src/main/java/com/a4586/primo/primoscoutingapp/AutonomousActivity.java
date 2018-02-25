package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;

public class AutonomousActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Button addSwitchBtn;
    private Button subSwitchBtn;
    private EditText pointSwitchEt;
    private Button addScaleBtn;
    private Button subScaleBtn;
    private EditText pointScaleEt;
    private Switch autoLine;
    private Button teleBtn;

    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonomous);
        addSwitchBtn = (Button) findViewById(R.id.addSwitchBtn);
        addScaleBtn = findViewById(R.id.addScaleBtn);
        subScaleBtn = findViewById(R.id.subAutoScale);
        subSwitchBtn = findViewById(R.id.subAutoSwitch);
        pointSwitchEt = findViewById(R.id.switchNumEt);
        pointScaleEt = findViewById(R.id.scaleNumEt);
        autoLine = (Switch) findViewById(R.id.autoLine);
        teleBtn = (Button) findViewById(R.id.tele);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        addSwitchBtn.setOnClickListener(this);
        addScaleBtn.setOnClickListener(this);
        subSwitchBtn.setOnClickListener(this);
        subScaleBtn.setOnClickListener(this);
        teleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == teleBtn.getId()) {
            Toast.makeText(AutonomousActivity.this, "autonomous sent!", Toast.LENGTH_SHORT).show();
            scoutingArr[4] = pointSwitchEt.getText().toString();
            scoutingArr[5] = pointScaleEt.getText().toString();
            if (autoLine.isChecked()) {
                scoutingArr[6] = "true";// adds true if crossed auto line
            } else {
                scoutingArr[6] = "false";// adds false if didn't crossed auto line
            }


            Intent intent = new Intent(AutonomousActivity.this, TeleopActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();


        }
        if (v.getId() == addSwitchBtn.getId()) {
            pointSwitchEt.setText("" + (Integer.parseInt(pointSwitchEt.getText().toString()) + 1));
        }
        if (v.getId() == addScaleBtn.getId()) {
            pointScaleEt.setText("" + (Integer.parseInt(pointScaleEt.getText().toString()) + 1));
        }
        if (v.getId() == subSwitchBtn.getId()) {
            pointSwitchEt.setText("" + (Integer.parseInt(pointSwitchEt.getText().toString()) - 1));
        }
        if (v.getId() == subScaleBtn.getId()) {
            pointScaleEt.setText("" + (Integer.parseInt(pointScaleEt.getText().toString()) - 1));
        }
    }
}
