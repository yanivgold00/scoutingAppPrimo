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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.Serializable;

public class AutonomousActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Button addSwitchBtn;
    private Button subSwitchBtn;
    private EditText pointSwitchEt;
    private Button addScaleBtn;
    private Button subScaleBtn;
    private EditText pointScaleEt;
    private Switch autoLine;
    private Button teleBtn;

    private String[] scoutingArr;

    Context context;
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    boolean pauseMusic = true;
    Menu mainMenu = null;
    private ServiceConnection Scon = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonomous);

        context = this; // This screen
        //Music handle
        musicService = new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this, MusicThread.class);
        startService(musicService);

        addSwitchBtn = (Button) findViewById(R.id.addSwitchBtn);
        addScaleBtn = findViewById(R.id.addScaleBtn);
        subScaleBtn = findViewById(R.id.subAutoScale);
        subSwitchBtn = findViewById(R.id.subAutoSwitch);
        pointSwitchEt = findViewById(R.id.switchNumEt);
        pointScaleEt = findViewById(R.id.scaleNumEt);
        autoLine = (Switch) findViewById(R.id.autoLine);
        teleBtn = (Button) findViewById(R.id.tele);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");

        addSwitchBtn.setOnClickListener(this);
        addScaleBtn.setOnClickListener(this);
        subSwitchBtn.setOnClickListener(this);
        subScaleBtn.setOnClickListener(this);
        teleBtn.setOnClickListener(this);
    }

    //Action bar handle
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bulletmenu, menu);
        mainMenu = menu;
        return true;
    }

    //Menu press should open 3 dot menu
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            mainMenu.performIdentifierAction(R.id.call, 0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Click listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.call:
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
                startActivity(call);
                break;
            case R.id.exit:
                //Close the app
                finish();
                break;
            case R.id.toggleMusic:
                mServ.toggleMusic();
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
        Intent setIntent = new Intent(this, GameActivity.class);
        setIntent.putExtras(getIntent());
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pauseMusic = false;
        startActivity(setIntent);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == teleBtn.getId()) {
            Toast.makeText(AutonomousActivity.this, "autonomous sent!", Toast.LENGTH_SHORT).show();
            scoutingArr[5] = pointSwitchEt.getText().toString();
            scoutingArr[6] = pointScaleEt.getText().toString();
            if (autoLine.isChecked()) {
                scoutingArr[4] = "true";// adds true if crossed auto line
            } else {
                scoutingArr[4] = "false";// adds false if didn't crossed auto line
            }


            Intent intent = new Intent(AutonomousActivity.this, TeleopActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            intent.putExtra("level", getIntent().getStringExtra("level"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);
        }
        if (v.getId() == addSwitchBtn.getId()) {
            pointSwitchEt.setText("" + (Integer.parseInt(pointSwitchEt.getText().toString()) + 1));
        }
        if (v.getId() == addScaleBtn.getId()) {
            pointScaleEt.setText("" + (Integer.parseInt(pointScaleEt.getText().toString()) + 1));
        }
        if (v.getId() == subSwitchBtn.getId()) {
            pointSwitchEt.setText("" + (Integer.parseInt(pointSwitchEt.getText().toString()) - 1));
        }
        if (v.getId() == subScaleBtn.getId()) {
            pointScaleEt.setText("" + (Integer.parseInt(pointScaleEt.getText().toString()) - 1));
        }
    }
}
