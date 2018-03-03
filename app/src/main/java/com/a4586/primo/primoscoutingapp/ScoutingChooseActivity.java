package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class ScoutingChooseActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Button gameBtn;
    private Button pitBtn;
    private Button infoBtn;
    private boolean isInfo;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_choose);
        gameBtn = (Button) findViewById(R.id.gameBtn);
        pitBtn = (Button) findViewById(R.id.pitBtn);
        infoBtn = (Button) findViewById(R.id.infoBtn);
        isInfo = false;
        level = getIntent().getStringExtra("level");
        if (level.equals("admin")) {
            infoBtn.setText("תוצאות");
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);
        }
        else if (level.equals("strat")) {
            infoBtn.setText("אסטרטגיה");
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);

        }

        gameBtn.setOnClickListener(this);
        pitBtn.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        String[] scoutingArr;
        if (v.getId() == gameBtn.getId()) {
            if (!isInfo) {
                Toast.makeText(ScoutingChooseActivity.this, "Game Form", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScoutingChooseActivity.this, GameActivity.class);
                scoutingArr = new String[19];
                scoutingArr[0] = getIntent().getStringExtra("name");
                intent.putExtra("scoutingArr", scoutingArr);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this,ResultsActivity.class);
                intent.putExtra("type","game");
                intent.putExtra("level",getIntent().getStringExtra("level"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        }



        if (v.getId() == pitBtn.getId()) {
            if(!isInfo) {
                Toast.makeText(ScoutingChooseActivity.this, "Pit Form", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScoutingChooseActivity.this, PitFormActivity.class);
                scoutingArr = new String[17];
                scoutingArr[0] = getIntent().getStringExtra("name");
                intent.putExtra("scoutingArr", scoutingArr);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this,TeamScoutPickActivity.class);
                intent.putExtra("type","pit");
                intent.putExtra("level",getIntent().getStringExtra("level"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        }

        if (v.getId() == infoBtn.getId()) {
            if (!isInfo) {
                if (level.equals("strat")) {
                    Intent intent = new Intent(this,CommentActivity.class);
                    intent.putExtra("level",getIntent().getStringExtra("level"));
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    startActivity(intent);
                } else {
                    isInfo = true;
                }
            }
            else {
                Intent intent = new Intent(this,TeamScoutPickActivity.class);
                intent.putExtra("type","comments");
                intent.putExtra("level",getIntent().getStringExtra("level"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        }

    }
}
