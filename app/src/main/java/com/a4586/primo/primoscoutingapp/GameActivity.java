package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
    private Spinner positionSpinner;
    private String[] scoutingArr;

    private ArrayAdapter adapter;
    private ArrayList<String> positionList;

    Context context;
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    Menu mainMenu = null;
    boolean pauseMusic = true;
    private ServiceConnection Scon  =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);

        autoBtn = (Button) findViewById(R.id.autoBtn);
        gameNum = (EditText) findViewById(R.id.gameNum);
        teamNum = (EditText) findViewById(R.id.teamNum);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");


        positionList = new ArrayList<>();
        positionList.add("ימין");
        positionList.add("מרכז");
        positionList.add("שמאל");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, positionList);
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        positionSpinner.setAdapter(adapter);

        autoBtn.setOnClickListener(this);

        if (getIntent().getStringArrayExtra("scoutingArr")[1] != null) {
            int num = Integer.parseInt((getIntent().getStringArrayExtra("scoutingArr")[2]));
            num++;
            gameNum.setText("" + num);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == autoBtn.getId()) {

            String teamNumber = teamNum.getText().toString();
            if (gameNum.getText().length()==0) {
                scoutingArr[2] = "0";
            }
            else {
                scoutingArr[2] = gameNum.getText().toString();//adds scouted game number
            }

            while (teamNumber.length()<4) {
                teamNumber = 0 + teamNumber;
            }
            scoutingArr[1] = teamNumber;//adds scouted team number
            scoutingArr[3] = positionSpinner.getSelectedItem().toString();//adds scouted team starting position

            Intent intent = new Intent(GameActivity.this, AutonomousActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            intent.putExtra("level", getIntent().getStringExtra("level"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);

        }

    }

    //Action bar handle
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bulletmenu, menu);
        mainMenu=menu;
        return true;
    }
    //Menu press should open 3 dot menu
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_MENU) {
            mainMenu.performIdentifierAction(R.id.call, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //Click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.call:
                Intent call= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
                startActivity(call);
                break;
            case R.id.exit:
                //Close the app
                finish();
                break;
            case R.id.toggleMusic:
                mServ.toogleMusic();
        }
        return true;
    }
    //Music bind and Unbind
    private void doBindService() {
        bindService(new Intent(context, MusicThread.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (pauseMusic) {
            mServ.stopMusic();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mServ.startMusic();
        doBindService();
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(this, ScoutingChooseActivity.class);
        setIntent.putExtras(getIntent());
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pauseMusic = false;
        startActivity(setIntent);

    }
}
