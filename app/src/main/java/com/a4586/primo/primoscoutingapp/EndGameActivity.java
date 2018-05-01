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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.Serializable;
import java.util.ArrayList;

public class EndGameActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
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
    private Switch reachedPlatform;
    private Spinner climbedSpinner;
    private Spinner helpedClimbSpinner;
    private Switch climbedFast;
    private Button nextScreenBtn;
    private ArrayAdapter adapter;
    private ArrayList<String> spinnerArray;
    private String[] scoutingArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);

        reachedPlatform = findViewById(R.id.reachPlatformSwitch);
        climbedSpinner = findViewById(R.id.climbedSpinner);
        helpedClimbSpinner = findViewById(R.id.helpedClimbSpinner);
        climbedFast = findViewById(R.id.wasfastSwitch);
        nextScreenBtn = (Button) findViewById(R.id.nextScreenBtn);

        spinnerArray = new ArrayList<>();
        spinnerArray.add("");
        spinnerArray.add("כן");
        spinnerArray.add("לא");
        spinnerArray.add("נעזר בפלטפורמה של רובוט אחר");
        spinnerArray.add("נעזר במוט מוארך של רובוט אחר");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        climbedSpinner.setAdapter(adapter);

        spinnerArray = new ArrayList<>();
        spinnerArray.add("");
        spinnerArray.add("לא עזר");
        spinnerArray.add("פתח פלטפורמה");
        spinnerArray.add("פתח מוט לטיפוס רובוט אחר");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        helpedClimbSpinner.setAdapter(adapter);

        scoutingArr = getIntent().getStringArrayExtra("scoutingArr");
        nextScreenBtn.setClickable(true);
        nextScreenBtn.setOnClickListener(this);
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
        if (v.getId() == nextScreenBtn.getId()) {
            nextScreenBtn.setClickable(false);

            scoutingArr[12] = reachedPlatform.isChecked() + "";
            scoutingArr[13] = climbedSpinner.getSelectedItem().toString();

            scoutingArr[14] = helpedClimbSpinner.getSelectedItem().toString();
            scoutingArr[15] = climbedFast.isChecked() + "";
            Intent intent = new Intent(this, SendActivity.class);
            intent.putExtra("scoutingArr", scoutingArr);
            intent.putExtra("level", getIntent().getStringExtra("level"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pauseMusic = false;
            startActivity(intent);

        }


    }

}
