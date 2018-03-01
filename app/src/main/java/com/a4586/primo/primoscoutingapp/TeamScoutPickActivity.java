package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TeamScoutPickActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText teamET;
    private Button contBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_scout_pick);
        teamET = findViewById(R.id.teamET);
        contBtn = findViewById(R.id.contBtn);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == contBtn.getId())
        {
            Intent intent = new Intent(this,ResultsActivity.class);
            intent.putExtras(getIntent().getExtras());
            intent.putExtra("team",teamET.getText().toString());
            startActivity(intent);
        }
    }
}
