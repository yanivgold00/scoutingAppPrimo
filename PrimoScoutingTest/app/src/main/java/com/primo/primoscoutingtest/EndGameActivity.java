package com.primo.primoscoutingtest;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.javalite.http.Http;
import org.javalite.http.Post;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class EndGameActivity  extends AppCompatActivity implements View.OnClickListener, Serializable  {

    private Spinner midSqSpnner;
    private Switch controlSq;
    private SeekBar speedBar;
    private Switch didDefence;
    private Switch crashed;
    private EditText commentText;
    private Button send;
    private ArrayAdapter adapter;
    private ArrayList<String> midControl;
    private String[] scoutingArr;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        midSqSpnner = (Spinner)findViewById(R.id.midSquare);
        midControl = new ArrayList<>();
        midControl.add("אין");
        midControl.add("כחול");
        midControl.add("אדום");
        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,midControl);
        midSqSpnner.setAdapter(adapter);

        controlSq = (Switch)findViewById(R.id.controlSquareSwitch);

        speedBar = (SeekBar) findViewById(R.id.speedSeekBar);

        didDefence = (Switch) findViewById(R.id.didDefence);
        crashed = (Switch) findViewById(R.id.crashed);
        commentText = (EditText) findViewById(R.id.commentText);
        send = (Button) findViewById(R.id.send);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        send.setOnClickListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == send.getId()) {

            scoutingArr[15]=midSqSpnner.getSelectedItem().toString();// adds who controls the middle square

            if(controlSq.isChecked())
            {
                scoutingArr[16]="true";// adds true if the robot controlled the control square
            }
            else
            {
                scoutingArr[16]="false";// adds false if the robot doesn't controlled the control square
            }

            String str = "" + speedBar.getProgress();
            scoutingArr[17]= str;// adds the robot speed from 1 to 5

            if (didDefence.isChecked()) {
                scoutingArr[18]="true";// adds true if robot acted as defence
            } else {
                scoutingArr[18]="false";// adds false if robot didn't act as defence
            }

            if (crashed.isChecked()) {
                scoutingArr[19]="true";// adds true if the robot crashed
            } else {
                scoutingArr[19]="false";// adds false if the robot didn't crashed
            }

            scoutingArr[20]=this.commentText.getText().toString();//adds the scouters comment on the robot

            SendForm();


        }



    }

    private void SendForm() {
    try {


        String formUrl = "https://docs.google.com/forms/d/e/1FAIpQLSfeQIgs2_VqTAr6yJebEUg8uQLdpPS8eaLuTNVpGnTOMwyXZQ/formResponse?usp=sf_link";


        Log.i("EndGameActivity","a");
        Post post = Http.post(formUrl)
                .param("entry.1643436835", scoutingArr[0])// sends name
                .param("entry.354921401", scoutingArr[1])// sends game number
                .param("entry.12390868", scoutingArr[2])// sends team number
                .param("entry.343530475", scoutingArr[3])// sends team color
                .param("entry.889024627", scoutingArr[4])// sends if robot tried gear in auto
                .param("entry.1301171502", scoutingArr[5])// sends if sucsseded gear in auto
                .param("entry.1209713886", scoutingArr[6])// sends which gear was tried in auto
                .param("entry.1619674572", scoutingArr[7])// sends if shot in auto
                .param("entry.906223791", scoutingArr[8])// sends how many shot in auto
                .param("entry.789462415", scoutingArr[9])// sends if crossed auto line
                .param("entry.219577677", scoutingArr[10])// sends if controlled the control square in auto
                .param("entry.1923472389", scoutingArr[11])// sends how many gears in static peg
                .param("entry.1865026753", scoutingArr[12])// sends how many gears in dinamic peg
                .param("entry.519994802", scoutingArr[13])// sends if shot teleop
                .param("entry.2128481740", scoutingArr[14])// sends how many fuel shot
                .param("entry.1420374806", scoutingArr[15])// sends who controlled middle square
                .param("entry.801141478", scoutingArr[16])// sends if controlled control square
                .param("entry.1270360043", scoutingArr[17])// sends robot speed
                .param("entry.1931667417", scoutingArr[18])// sends if played as defence
                .param("entry.1587862404", scoutingArr[19])// sends if crashed
                .param("entry.2058507011", scoutingArr[20]);//sends comments
        Log.i("EndGameActivity","b");

        Log.i("EndGameActivity","c");
        if (post.text() != null)
            Toast.makeText(EndGameActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EndGameActivity.this, GameActivity.class);
        intent.putExtra("scoutingArr", scoutingArr);
        startActivity(intent);
    }
    catch (Exception e)
    {
        Log.i("EndGameActivity",e.getStackTrace().toString());
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }
    }


}
