package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText teamET;
    private EditText commentET;
    private Button sendBtn;
    private Button backBtn;
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    Context context;
    Intent musicService;
    private boolean mIsBound = false;
    private MusicThread mServ;
    Menu mainMenu = null;
    boolean pauseMusic = true;
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
        setContentView(R.layout.activity_comment);

        context = this; // This screen
        //Music handle
        musicService = new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this, MusicThread.class);
        startService(musicService);

        teamET = findViewById(R.id.teamET);
        commentET = findViewById(R.id.commentsET);
        sendBtn = findViewById(R.id.sendBtn);
        backBtn = findViewById(R.id.backBtn);

        sendBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==sendBtn.getId()) {
            sendFireBase();
            }
        if (view.getId()==backBtn.getId()) {
            Intent intent = new Intent(this,ScoutingChooseActivity.class);
            intent.putExtra("level",getIntent().getStringExtra("level"));
            intent.putExtra("name",getIntent().getStringExtra("name"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);
        }
    }

    private void sendFireBase() {
        database.collection("comments").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    int counter = 0;
//                    DocumentReference doc = database.collection("games").document(scoutingArr[1]);
                    String teamNumber = teamET.getText().toString();

                    for (DocumentSnapshot doc:task.getResult().getDocuments()) {
                        counter++;

                    }

                    Map<String, Object> comment = new HashMap<>();
                    comment.put("name",getIntent().getStringExtra("name"));
                    comment.put("team",teamNumber);
                    comment.put("comment",commentET.getText().toString());

                    database.collection("comments").document(teamNumber+counter).set(comment);
                    Log.v("TAG", "succes");
                    Toast.makeText(CommentActivity.this, "everything sent! yay:)", Toast.LENGTH_SHORT).show();
                    teamET.setText("");
                    commentET.setText("");

                } else {
                    Toast.makeText(CommentActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    Log.v("TAG", "error");

                }
            }
        });
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
