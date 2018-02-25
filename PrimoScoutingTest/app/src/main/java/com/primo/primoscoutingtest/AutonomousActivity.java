package com.primo.primoscoutingtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class AutonomousActivity extends AppCompatActivity implements View.OnClickListener, Serializable{
    private Switch tryGear;
    private Switch sucGear;
    private Spinner gearSpinner;
    private Switch didShot;
    private EditText fuelFired;
    private Switch autoLine;
    private Button teleBtn;

    private ArrayList<String> gearList;
    private ArrayAdapter adapter;
    private String[] scoutingArr;
    private Switch controlSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonomous);
        tryGear=(Switch)findViewById(R.id.tryGear);
        sucGear=(Switch)findViewById(R.id.sucGear);
        gearSpinner=(Spinner)findViewById(R.id.gearSpinner);
        didShot=(Switch)findViewById(R.id.didShoot);
        fuelFired=(EditText)findViewById(R.id.fuelFired);
        autoLine=(Switch)findViewById(R.id.autoLine);
        teleBtn=(Button)findViewById(R.id.tele);
        controlSquare=(Switch)findViewById(R.id.controlSquareSwitch);

        gearList = new ArrayList<>();
        gearList.add("אין");
        gearList.add("ימין");
        gearList.add("שמאל");

        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,gearList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gearSpinner.setAdapter(adapter);
        gearSpinner.setEnabled(true);
        gearSpinner.setClickable(true);

        scoutingArr=getIntent().getStringArrayExtra("scoutingArr");

        teleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==teleBtn.getId())
        {
            Toast.makeText(AutonomousActivity.this, "autonomous sent!", Toast.LENGTH_SHORT).show();

            if(tryGear.isChecked()){
                scoutingArr[4]="true";// adds true if robot tried gear in auto
            }
            else{
                scoutingArr[4]="false";// adds false if robot didn't try gear in auto
            }

            if(sucGear.isChecked()){
                scoutingArr[5]="true";// adds true if robot put gear in auto
            }
            else{
                scoutingArr[5]="false";// adds false if robot didn't put gear in auto
            }

            scoutingArr[6]=gearSpinner.getSelectedItem().toString();// adds the side of gear tried

            if(didShot.isChecked()){
                scoutingArr[7]="true";// adds true if the robot shot in auto
            }
            else{
                scoutingArr[7]="false";// adds false if the robot didn't shot in auto
            }

            if(fuelFired.getText().toString().equals(""))
            {
                scoutingArr[8]="0";// adds zero if nothing is written
            }
            else
            {
                scoutingArr[8]=fuelFired.getText().toString();// adds number of fuel fired
            }
            if(autoLine.isChecked()){
                scoutingArr[9]="true";// adds true if crossed auto line
            }
            else{
                scoutingArr[9]="false";// adds false if didn't crossed auto line
            }

            if(controlSquare.isChecked()){
                scoutingArr[10]="true";// adds true if controlled the control square
            }
            else{
                scoutingArr[10]="false";// adds false if didn't controlled the control square
            }

            Intent intent = new Intent(AutonomousActivity.this,TeleopActivity.class);
            intent.putExtra("scoutingArr",scoutingArr);
            startActivity(intent);


        }
    }
}
