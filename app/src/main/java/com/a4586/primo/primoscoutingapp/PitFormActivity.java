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
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class PitFormActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private EditText teamNumET;
    private EditText teamNameET;
    private Button contBtn;
    private String[] scoutingArr;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_form);

        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        teamNumET = (EditText) findViewById(R.id.teamNum);

        teamNameET = (EditText) findViewById(R.id.teamName);

        contBtn = (Button) findViewById(R.id.contBtn);

        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == contBtn.getId()) {
            String teamNumber = teamNumET.getText().toString();
            if(teamNumber.length()>0&& teamNameET.getText().toString().length()>0) {
                while (teamNumber.length() < 4) {
                    teamNumber = 0 + teamNumber;
                }
                scoutingArr[1] = teamNumber;
                scoutingArr[2] = teamNameET.getText().toString();
                Intent intent = new Intent(PitFormActivity.this, PitMainActivity.class);
                intent.putExtra("scoutingArr", scoutingArr);
                startActivity(intent);
            }

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
    public void onBackPressed(){
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
}
