package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;

public class EndGameActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Switch reachedPlatform;
    private Spinner climbedSpinner;
    private Spinner helpedClimbSpinner;
    private Switch climbedFast;
    private Button nextScreenBtn;
    private ArrayAdapter adapter;
    private ArrayList<String> spinnerArray;
    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        reachedPlatform = findViewById(R.id.reachPlatformSwitch);
        climbedSpinner = findViewById(R.id.climbedSpinner);
        helpedClimbSpinner = findViewById(R.id.helpedClimbSpinner);
        climbedFast = findViewById(R.id.wasfastSwitch);
        nextScreenBtn = (Button) findViewById(R.id.nextScreenBtn);

        spinnerArray = new ArrayList<>();
        spinnerArray.add("");
        spinnerArray.add("כן");
        spinnerArray.add("לא");
        spinnerArray.add("נעזר בפלטפורמה של רובוט אחר");
        spinnerArray.add("נעזר במוט מוארך של רובוט אחר");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        climbedSpinner.setAdapter(adapter);

        spinnerArray = new ArrayList<>();
        spinnerArray.add("");
        spinnerArray.add("לא עזר");
        spinnerArray.add("פתח פלטפורמה");
        spinnerArray.add("פתח מוט לטיפוס רובוט אחר");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        helpedClimbSpinner.setAdapter(adapter);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        nextScreenBtn.setClickable(true);
        nextScreenBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == nextScreenBtn.getId()) {
            nextScreenBtn.setClickable(false);

            scoutingArr[12] = reachedPlatform.isChecked() + "";
            scoutingArr[13] = climbedSpinner.getSelectedItem().toString();

            scoutingArr[14] = helpedClimbSpinner.getSelectedItem().toString();
            scoutingArr[15] = climbedFast.isChecked() + "";
            Intent intent = new Intent(this, SendActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();
        }


    }

}
