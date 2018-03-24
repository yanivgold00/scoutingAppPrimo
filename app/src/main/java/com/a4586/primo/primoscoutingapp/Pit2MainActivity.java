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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.javalite.http.Http;
import org.javalite.http.Post;

import java.util.HashMap;
import java.util.Map;

public class Pit2MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CheckBox rightAutoSwitchCB, midAutoSwitchCB, leftAutoSwitchCB;
    private CheckBox rightAutoScaleCB, midAutoScaleCB, leftAutoScaleCB;
    private EditText drivingSystemET;
    private EditText wheelTypeET;
    private EditText stratET;
    private EditText massET;
    private EditText issueET;
    private Button sendBtn;


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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_pit2_main);
        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);
        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        drivingSystemET = (EditText) findViewById(R.id.drivingSystemET);
        wheelTypeET = (EditText) findViewById(R.id.wheelTypeET);
        stratET = (EditText) findViewById(R.id.stratET);
        issueET = (EditText) findViewById(R.id.issueET);
        rightAutoScaleCB = findViewById(R.id.rightAutoScale);
        midAutoScaleCB = findViewById(R.id.midAutoScale);
        leftAutoScaleCB = findViewById(R.id.leftAutoScale);

        rightAutoSwitchCB = findViewById(R.id.rightAutoSwitch);
        midAutoSwitchCB =findViewById(R.id.midAutoSwitch);
        leftAutoSwitchCB = findViewById(R.id.leftAutoSwitch);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setClickable(true);
        sendBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if ((v.getId() == sendBtn.getId())) {
            sendBtn.setClickable(false);
            scoutingArr[11] = "";
            if(rightAutoSwitchCB.isChecked()) {
                scoutingArr[11] += "ימין,";
            }
            if(midAutoSwitchCB.isChecked()) {
                scoutingArr[11] += "מרכז,";
            }
            if(leftAutoSwitchCB.isChecked()) {
                scoutingArr[11] += "שמאל,";
            }

            scoutingArr[12] = "";
            if(rightAutoScaleCB.isChecked()) {
                scoutingArr[12] += "ימין,";
            }
            if(midAutoScaleCB.isChecked()) {
                scoutingArr[12] += "מרכז,";
            }
            if(leftAutoScaleCB.isChecked()) {
                scoutingArr[12] += "שמאל,";
            }
            scoutingArr[13] = drivingSystemET.getText().toString();
            scoutingArr[14] = wheelTypeET.getText().toString();
            scoutingArr[15] = stratET.getText().toString();
            scoutingArr[16] = issueET.getText().toString();


            sendForm();
            Toast.makeText(Pit2MainActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Pit2MainActivity.this, PitFormActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            startActivity(intent);
            finish();
        }
    }

    private void sendForm() {
        database.collection("teams").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    Map<String, Object> team = new HashMap<>();
                    team.put("scouter",scoutingArr[0]);
                    team.put("number", scoutingArr[1]);
                    team.put("name", scoutingArr[2]);
                    team.put("role", scoutingArr[3]);
                    team.put("role_comment", scoutingArr[4]);
                    team.put("cubes_at", scoutingArr[5]);
                    team.put("cube_system", scoutingArr[6]);
                    team.put("does_climb", scoutingArr[7]);
                    team.put("helps_climb", scoutingArr[8]);
                    team.put("auto_line", scoutingArr[9]);
                    team.put("robot_mass", scoutingArr[10]);
                    team.put("auto_switch", scoutingArr[11]);
                    team.put("auto_scale", scoutingArr[12]);
                    team.put("driving_system", scoutingArr[13]);
                    team.put("wheel_type", scoutingArr[14]);
                    team.put("strategy", scoutingArr[15]);
                    team.put("problems", scoutingArr[16]);

                    database.collection("teams").document(scoutingArr[1]).set(team);
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
