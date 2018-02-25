package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class ScoutingChooseActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Button gameBtn;
    private Button pitBtn;
    private Button infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_choose);
        gameBtn = (Button) findViewById(R.id.gameBtn);
        pitBtn = (Button) findViewById(R.id.pitBtn);
        infoBtn = (Button) findViewById(R.id.infoBtn);

        if (getIntent().getBooleanExtra("isAdmin", false)) {
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);
        }

        gameBtn.setOnClickListener(this);
        pitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String[] scoutingArr;
        if (v.getId() == gameBtn.getId()) {
            Toast.makeText(ScoutingChooseActivity.this, "Game Form", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScoutingChooseActivity.this, GameActivity.class);
            scoutingArr = new String[21];
            scoutingArr[0] = getIntent().getStringExtra("name");
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }

        if (v.getId() == pitBtn.getId()) {
            Toast.makeText(ScoutingChooseActivity.this, "Pit Form", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ScoutingChooseActivity.this, PitFormActivity.class);
            scoutingArr = new String[19];
            scoutingArr[0] = getIntent().getStringExtra("name");
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }

        if (v.getId() == infoBtn.getId()) {
            Intent intent = new Intent(ScoutingChooseActivity.this, ResultsActivity.class);
            startActivity(intent);
        }

    }
}
