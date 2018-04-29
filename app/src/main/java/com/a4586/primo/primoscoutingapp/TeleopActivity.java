package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class TeleopActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    Context context;
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    Menu mainMenu = null;
    private ServiceConnection Scon  =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };
    private Spinner cycleTookFromSpinner;
    private Spinner cyclePutInSpinner;
    private Button nextCycleBtn;
    private Button endGameBtn;
    private String[] scoutingArr;
    private ArrayList<String> tookFrom;
    private ArrayList<String> putIn;

    private ArrayAdapter adapter;

    private int tookFeeder, tookFloor, putScale, putSwitch, putVault;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);

        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);

        cyclePutInSpinner = findViewById(R.id.cyclePutInSpinner);
        cycleTookFromSpinner = findViewById(R.id.cycleTookFromSpinner);
        nextCycleBtn = findViewById(R.id.nextCycleBtn);
        endGameBtn = findViewById(R.id.endGameBtn);

        tookFrom = new ArrayList<>();
        tookFrom.add(" ");
        tookFrom.add("רצפה");
        tookFrom.add("פידר/אקסצ'יינג'");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tookFrom);
        cycleTookFromSpinner.setAdapter(adapter);

        putIn = new ArrayList<>();
        putIn.add(" ");
        putIn.add("סקייל");
        putIn.add("סוויץ'");
        putIn.add("אקסצ'יינג'");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, putIn);
        cyclePutInSpinner.setAdapter(adapter);

        tookFeeder = 0;
        tookFloor = 0;
        putScale = 0;
        putSwitch = 0;
        putVault = 0;

        endGameBtn.setOnClickListener(this);
        nextCycleBtn.setOnClickListener(this);
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
    private void doBindService(){
        bindService(new Intent(context,MusicThread.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mIsBound) {
            mServ.stopMusic();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsBound) {
            mServ.startMusic();
        }
    }
    @Override
    public void onUserLeaveHint() {
        mServ.stopMusic();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == endGameBtn.getId()) {
            if (cycleTookFromSpinner.getSelectedItem().toString().equals("רצפה")) {
                tookFloor++;
            } else if (cycleTookFromSpinner.getSelectedItem().toString().equals("פידר/אקסצ'יינג'")) {
                tookFeeder++;
            }

            if (cyclePutInSpinner.getSelectedItem().toString().equals("סקייל")) {
                putScale++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("סוויץ'")) {
                putSwitch++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("אקסצ'יינג'")) {
                putVault++;
            }
            scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
            scoutingArr[8] = tookFloor + "";
            scoutingArr[7] = tookFeeder + "";
            scoutingArr[11] = putVault + "";
            scoutingArr[10] = putSwitch + "";
            scoutingArr[9] = putScale + "";

            Intent intent = new Intent(TeleopActivity.this, EndGameActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();
        }

        if (v.getId() == nextCycleBtn.getId()) {
            if (cycleTookFromSpinner.getSelectedItem().toString().equals("רצפה")) {
                tookFloor++;
            } else if (cycleTookFromSpinner.getSelectedItem().toString().equals("פידר/אקסצ'יינג'")) {
                tookFeeder++;
            }

            if (cyclePutInSpinner.getSelectedItem().toString().equals("סקייל")) {
                putScale++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("סוויץ'")) {
                putSwitch++;
            } else if (cyclePutInSpinner.getSelectedItem().toString().equals("אקסצ'יינג'")) {
                putVault++;
            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tookFrom);
            cycleTookFromSpinner.setAdapter(adapter);

            adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, putIn);
            cyclePutInSpinner.setAdapter(adapter);
        }

    }
}
