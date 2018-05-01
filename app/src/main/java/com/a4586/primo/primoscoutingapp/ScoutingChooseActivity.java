package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import java.io.Serializable;

public class ScoutingChooseActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Button gameBtn;
    private Button pitBtn;
    private Button infoBtn;
    private Button gameCommentBtn;
    private boolean isInfo;
    String level;


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
        setContentView(R.layout.activity_scouting_choose);
        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);
        gameBtn = (Button) findViewById(R.id.gameBtn);
        pitBtn = (Button) findViewById(R.id.pitBtn);
        infoBtn = (Button) findViewById(R.id.infoBtn);
        gameCommentBtn = findViewById(R.id.gameCommentBtn);

        isInfo = false;
        level = getIntent().getStringExtra("level");

        if (level.equals("shalosH")) {
            infoBtn.setText("תוצאות");
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);
            gameCommentBtn.setText("הערות");
            gameCommentBtn.setClickable(true);
            gameCommentBtn.setVisibility(View.VISIBLE);
            gameCommentBtn.setOnClickListener(this);
        }
        else if (level.equals("Strat4586")) {
            infoBtn.setText("תוצאות");
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);
            gameCommentBtn.setText("הערות");
            gameCommentBtn.setClickable(true);
            gameCommentBtn.setVisibility(View.VISIBLE);
            gameCommentBtn.setOnClickListener(this);

        }
        else if(level.equals("Pass"))
        {
            pitBtn.setClickable(false);
            pitBtn.setVisibility(View.INVISIBLE);

        }
        else if (level.equals("Admin")) {
            pitBtn.setClickable(false);
            pitBtn.setVisibility(View.INVISIBLE);
            infoBtn.setText("תוצאות");
            infoBtn.setClickable(true);
            infoBtn.setVisibility(View.VISIBLE);
            infoBtn.setOnClickListener(this);
        }

        gameBtn.setOnClickListener(this);
        pitBtn.setOnClickListener(this);

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
                break;
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
    public void onDestroy(){
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
        Intent setIntent = new Intent(this,MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pauseMusic = false;
        startActivity(setIntent);

    }




    @Override
    public void onClick(View v) {
        String[] scoutingArr;
        if (v.getId() == gameBtn.getId()) {
            if (!isInfo) {
                Toast.makeText(ScoutingChooseActivity.this, "Game Form", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScoutingChooseActivity.this, GameActivity.class);
                scoutingArr = new String[19];
                scoutingArr[0] = getIntent().getStringExtra("name");
                intent.putExtra("scoutingArr", scoutingArr);
                intent.putExtra("level", level);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);

            }
            else {
                Intent intent = new Intent(this,TeamScoutPickActivity.class);
                intent.putExtra("type","game");
                intent.putExtra("level",level);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
            }
        }

        if (v.getId() == pitBtn.getId()) {
            if(!isInfo) {
                Toast.makeText(ScoutingChooseActivity.this, "Pit Form", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ScoutingChooseActivity.this, PitFormActivity.class);
                scoutingArr = new String[17];
                scoutingArr[0] = getIntent().getStringExtra("name");
                intent.putExtra("scoutingArr", scoutingArr);
                intent.putExtra("level", level);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this,TeamScoutPickActivity.class);
                intent.putExtra("type","pit");
                intent.putExtra("level",level);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
            }
        }

        if (v.getId() == infoBtn.getId()) {
            if (!isInfo) {

                if (level.equals("Admin")){
                    Intent intent = new Intent(this,TeamScoutPickActivity.class);
                    intent.putExtra("type","game");
                    intent.putExtra("level",level);
                    intent.putExtra("name",getIntent().getStringExtra("name"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);
                }
                else {
                    isInfo = true;
                    gameCommentBtn.setClickable(false);
                    gameCommentBtn.setVisibility(View.INVISIBLE);
                    infoBtn.setText("הערות");
                }
            }
            else {
                Intent intent = new Intent(this,TeamScoutPickActivity.class);
                intent.putExtra("type","comments");
                intent.putExtra("level",level);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);
            }
        }

        if (v.getId() == gameCommentBtn.getId()) {
            Intent intent = new Intent(this,CommentActivity.class);
            intent.putExtra("level",level);
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);
        }

    }
}

