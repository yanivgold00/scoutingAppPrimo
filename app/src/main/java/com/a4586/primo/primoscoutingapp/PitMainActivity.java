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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;


public class PitMainActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private Spinner roleSpinner;
    private EditText roleET;
    private CheckBox vaultCB, switchCB, scaleCB;
    private EditText cubeSystemET;
    private Switch climbsSwitch;
    private EditText helpsClimbET;
    private Switch baseLineSwitch;
    private Button contBtn;
    private EditText massET;

    private ArrayAdapter adapter;
    private ArrayList<String> roleList;
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
        setContentView(R.layout.activity_pit_main);

        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        roleList = new ArrayList<>();
        roleList.add("התקפה");
        roleList.add("הגנה");
        roleList.add("שניהם");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList);
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        roleSpinner.setAdapter(adapter);

        roleET = (EditText) findViewById(R.id.commentRoleET);

        vaultCB = findViewById(R.id.vaultCB);
        switchCB = findViewById(R.id.switchCB);
        scaleCB = findViewById(R.id.scaleCB);

        cubeSystemET = findViewById(R.id.cubeSystemET);

        climbsSwitch = findViewById(R.id.climbsSwitch);

        helpsClimbET = findViewById(R.id.helpsClimbET);

        baseLineSwitch = (Switch) findViewById(R.id.baseLineSwitch);

        contBtn = (Button) findViewById(R.id.contBtn);

        contBtn.setOnClickListener(this);

        massET = (EditText) findViewById(R.id.robotMassED);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == contBtn.getId()) {
            scoutingArr[3] = roleSpinner.getSelectedItem().toString();
            scoutingArr[4] = roleET.getText().toString();
            scoutingArr[5] = "";
            if(vaultCB.isChecked()){
                scoutingArr[5]+="אקסציינג', ";
            }
            if(switchCB.isChecked()){
                scoutingArr[5]+="סוויץ', ";
            }
            if(scaleCB.isChecked()){
                scoutingArr[5]+="סקייל, ";
            }

            scoutingArr[6] = cubeSystemET.getText().toString();

            scoutingArr[7] = climbsSwitch.isChecked()+"";

            scoutingArr[8] = helpsClimbET.getText().toString();

            scoutingArr[9] = baseLineSwitch.isChecked()+"";

            scoutingArr[10] = massET.getText().toString();

            Intent intent = new Intent(PitMainActivity.this, Pit2MainActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();
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
}
