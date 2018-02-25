package com.primo.primoscoutingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private Spinner teamSpinner;
    private String[] scoutingArr;

    private ArrayAdapter adapter;
    private ArrayList<String> teamColorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        autoBtn = (Button) findViewById(R.id.autoBtn);
        gameNum = (EditText) findViewById(R.id.gameNum);
        teamNum = (EditText) findViewById(R.id.teamNum);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");



        teamColorList = new ArrayList<>();
        teamColorList.add("אדום");
        teamColorList.add("כחול");
        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,teamColorList);
        teamSpinner=(Spinner)findViewById(R.id.teamSpinner);
        teamSpinner.setAdapter(adapter);

        autoBtn.setOnClickListener(this);

        if(getIntent().getStringArrayExtra("scoutingArr")[1]!=null)
        {
            int num = Integer.parseInt(getIntent().getStringArrayExtra("scoutingArr")[1]) + 1;
            gameNum.setText("" + num);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == autoBtn.getId())
        {

            scoutingArr[1]=gameNum.getText().toString();//adds scouted game number
            scoutingArr[2]=teamNum.getText().toString();//adds scouted team number
            scoutingArr[3]=teamSpinner.getSelectedItem().toString();//adds scouted team alliance

            Intent intent = new Intent(GameActivity.this, AutonomousActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
        }

    }
}
