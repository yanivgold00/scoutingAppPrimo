package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class PitFormActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private EditText teamNumET;
    private EditText teamNameET;
    private Button contBtn;
    private String[] scoutingArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_form);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        teamNumET = (EditText) findViewById(R.id.teamNum);

        teamNameET = (EditText) findViewById(R.id.teamName);

        contBtn = (Button) findViewById(R.id.contBtn);

        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == contBtn.getId()) {
            String teamNumber = teamNumET.getText().toString();
            if(teamNumber.length()>0&& teamNameET.getText().toString().length()>0) {
                while (teamNumber.length() < 4) {
                    teamNumber = 0 + teamNumber;
                }
                scoutingArr[1] = teamNumber;
                scoutingArr[2] = teamNameET.getText().toString();
                Intent intent = new Intent(PitFormActivity.this, PitMainActivity.class);
                intent.putExtra("scoutingArr", scoutingArr);
                startActivity(intent);
            }

        }
    }
}
