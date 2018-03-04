package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeamScoutPickActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText teamET;
    private Button contBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_scout_pick);
        teamET = findViewById(R.id.teamET);
        contBtn = findViewById(R.id.contBtn);
        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!(teamET.getText().toString().isEmpty()&&(getIntent().getStringExtra("level").equals("Admin")||!getIntent().getStringExtra("type").equals("game")))) {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtras(getIntent().getExtras());
            intent.putExtra("team", teamET.getText().toString());
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Team number empty", Toast.LENGTH_SHORT).show();
        }
    }
}
