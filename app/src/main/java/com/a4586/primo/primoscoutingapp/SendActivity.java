package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.NonNull;
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
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {
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
    ToggleButton roleBtn;
    Switch didCrashSwitch;
    EditText commentsET;
    Button sendBtn;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    String[] scoutingArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_send);
        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        roleBtn = findViewById(R.id.offenseDefenseBtn);
        roleBtn.setTextOff("התקפה");
        roleBtn.setTextOn("הגנה");
        didCrashSwitch = findViewById(R.id.didCrashSwitch);
        commentsET = findViewById(R.id.commentsET);
        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setClickable(true);

        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == sendBtn.getId()) {
            scoutingArr[16] = roleBtn.getText().toString();
            scoutingArr[17] = didCrashSwitch.isChecked() + "";
            scoutingArr[18] = commentsET.getText().toString();

            sendBtn.setClickable(false);
            sendFirebase();

        }

    }

    private void sendFirebase() {

        database.collection("games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    Map<String, Object> game = new HashMap<>();
                    // general
                    game.put("scouter",scoutingArr[0]);
                    game.put("team",scoutingArr[1]);
                    game.put("game_number",scoutingArr[2]);
                    game.put("start_position",scoutingArr[3]);
                    // auto
                    game.put("passed_auto_line", scoutingArr[4]);
                    game.put("auto_switch", scoutingArr[5]);
                    game.put("auto_scale", scoutingArr[6]);
                    //teleop
                    game.put("pick_feeder", scoutingArr[7]);
                    game.put("pick_floor", scoutingArr[8]);
                    game.put("put_scale", scoutingArr[9]);
                    game.put("put_switch", scoutingArr[10]);
                    game.put("put_vault", scoutingArr[11]);
                    //end game
                    game.put("reach_platform", scoutingArr[12]);
                    game.put("did_climb", scoutingArr[13]);
                    game.put("helped_climb",scoutingArr[14]);
                    game.put("climbed_fast",scoutingArr[15]);
                    //general game
                    game.put("role",scoutingArr[16]);
                    game.put("did_crash",scoutingArr[17]);
                    game.put("comments",scoutingArr[18]);

                    database.collection("games").document(scoutingArr[1]+scoutingArr[2]).set(game);
                    Log.v("TAG", "succes");
                    Toast.makeText(SendActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SendActivity.this, GameActivity.class);
                    intent.putExtra("scoutingArr", scoutingArr);
                    intent.putExtra("level", getIntent().getStringExtra("level"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pauseMusic = false;
                    startActivity(intent);



                } else {
                    Toast.makeText(SendActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    Log.v("TAG", "error");
                    sendBtn.setClickable(true);
                }
            }
        });

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
        Intent setIntent = new Intent(this, GameActivity.class);
        setIntent.putExtras(getIntent());
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pauseMusic = false;
        startActivity(setIntent);

    }


}
