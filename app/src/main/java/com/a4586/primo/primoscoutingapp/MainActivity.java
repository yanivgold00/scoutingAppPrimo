
package com.a4586.primo.primoscoutingapp;

import android.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    int myPremmision;
    Context context;
    Intent musicService;
    Intent intent;
    boolean pauseMusic = true;
    private boolean mIsBound = false;
    private MusicThread mServ;
    private ServiceConnection Scon  =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicThread.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };
    Menu mainMenu = null;
    private EditText pw;
    private EditText name;
    private Button loginBtn;
    private Button changeMusicBtn;
    static BatteryService batteryService = new BatteryService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = this.getApplicationContext().registerReceiver(batteryService, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //Should the request be displayed
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        myPremmision);
            }
        }
        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();


        musicService.setClass(this,MusicThread.class);
        startService(musicService);
        Log.d("TAG", "preBind");
        doBindService();

        pw = findViewById(R.id.pw);
        name = findViewById(R.id.Name);
        loginBtn = findViewById(R.id.loginBtn);
        changeMusicBtn = findViewById(R.id.changeMusicBtn);

        changeMusicBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    /**
     * Change music.
     */
    public void changeMusic() {
        ArrayList<File> songList = getPlayList(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music"); //Hold all the song on the phone
        ArrayList<String> songName = new ArrayList<>(); // List of song names
        ArrayList<String> songPath = new ArrayList<>(); // List of song paths
        final ArrayList<String> copySongPath = new ArrayList<>(); //Backup of the songPath as a final
        //If there are songs
        if (songList != null) {
            //Split song list to song name and path lists
            for (int i = 0; i < songList.size(); i++) {
                String fileName = songList.get(i).getName();
                String filePath = songList.get(i).getAbsolutePath();
                songName.add(fileName);
                songPath.add(filePath);
            }
        }
        copySongPath.addAll(songPath); // adding all the paths to the backup
        //Creating the dialog that display all the songs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView lv = new ListView(this); //List view to hold the songs
        LinearLayout main = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        main.setLayoutParams(lp);
        //Inserting the list to the thread
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songName);
        lv.setAdapter(arrayAdapter);
        main.addView(lv);
        builder.setView(main);

        final AlertDialog alert = builder.create();
        alert.show();
        //Click listener for all the songs in the list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                //Changing which song is playing
                stopService(musicService);
                mServ.changeSong(Uri.parse(copySongPath.get(pos)).toString());
                musicService.setClass(context, MusicThread.class);
                startService(musicService);
                alert.dismiss();
            }

        });
    }

    /**
     * Get the list of the all the music files
     *
     * @param rootPath the root path
     * @return the play list
     */

    private ArrayList<File> getPlayList(String rootPath) {
        Log.d("TAG", rootPath);
        ArrayList<File> fileList = new ArrayList<>(); // Holds the list of the songs
        File rootFolder = new File(rootPath); //The main folder
        File[] files = rootFolder.listFiles(); //All the files in the folder
        //If song add to the list if a directory go in side and add all the song there
        Log.d("TAG", ("" + rootFolder.isDirectory()));
        if (rootFolder.isDirectory()) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else {
                        break;
                    }
                    //Add only mp3 files
                } else if (file.getName().endsWith(".mp3")) {
                    fileList.add(file);
                }
            }
            Log.d("TAG", "finished");
        }
        return fileList;
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
                finish();
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
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == loginBtn.getId()) {
            //strategy team!!
            if (this.pw.getText().toString().equals("Strat4586")) {
                Toast.makeText(MainActivity.this, "You are logged in as strat", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);


            }
            //admin for PRIMO!!
            else if (this.pw.getText().toString().equals("shalosH")) {
                Toast.makeText(MainActivity.this, "You are logged in as admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);

            }
            //admin for Trigon
            else if(this.pw.getText().toString().equals("Admin"))
            {
                Toast.makeText(MainActivity.this, "You are logged in as admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);


            }
            //scouter Trigon
            else if (this.pw.getText().toString().equals("Pass"))
            {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);


            }
            //scouter for PRIMO!!
            else if (this.pw.getText().toString().equals("amitlaba0") || this.pw.getText().toString().equals("microgali0"))
            {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                pauseMusic = false;
                startActivity(intent);


            }

            //wrong password
            else {
                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        } else if (this.changeMusicBtn.getId() == v.getId()) {
            changeMusic();
        }
    }
}
