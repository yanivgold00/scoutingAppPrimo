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
import android.widget.Toast;

public class TeamScoutPickActivity extends AppCompatActivity implements View.OnClickListener {
    // Menu
    Menu mainMenu = null;

    //Context
    Context context;

    //Music
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    boolean pauseMusic = true;
    private ServiceConnection Scon = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    //UI objects
    private EditText teamET;
    private Button contBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_scout_pick);

        context = this; // This screen

        //Music handle
        musicService = new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this, MusicThread.class);
        startService(musicService);

        //UI handle
        teamET = findViewById(R.id.teamET);
        contBtn = findViewById(R.id.contBtn);

        //Set click listener
        contBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Checks if user filled team/game number and if not checks if user searches for correct information type and has clearance for the information
        if (!(teamET.getText().toString().isEmpty() && (getIntent().getStringExtra("level").equals("Admin") || !getIntent().getStringExtra("type").equals("game")))) {
            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtras(getIntent());
            intent.putExtra("team", teamET.getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);
        } else {
            Toast.makeText(this, "Team number empty", Toast.LENGTH_SHORT).show();
        }
    }

    //Menu handle
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

    //Menu options click listener
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

    //Music binder and Unbinder
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

    //Music handle with activity
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

    //Back press handle
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
