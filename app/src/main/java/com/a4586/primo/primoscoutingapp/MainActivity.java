
package com.a4586.primo.primoscoutingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    Context context;
    Intent musicService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this; // This screen
        //Music handle
        musicService= new Intent();
        mServ = new MusicThread();
        doBindService();
        musicService.setClass(this,MusicThread.class);
        startService(musicService);
        pw = (EditText) findViewById(R.id.pw);
        name = (EditText) findViewById(R.id.Name);
        loginBtn = (Button) findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(this);

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
    @Override
    public void onUserLeaveHint() {
        mServ.stopMusic();
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
    public void onDestroy(){
        super.onDestroy();
        doUnbindService();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBtn.getId()) {
//            if (this.pw.getText().toString().equals("microgali0") || this.pw.getText().toString().equals("amitlaba0")||this.pw.getText().toString().equals("strat")) {
            //strategy team!!
            if (this.pw.getText().toString().equals("Strat4586")) {
                Toast.makeText(MainActivity.this, "You are logged in as strat", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                startActivity(intent);

            }
            //admin for PRIMO!!
            else if (this.pw.getText().toString().equals("shalosH")) {
                Toast.makeText(MainActivity.this, "You are logged in as admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                startActivity(intent);
            }
            //admin for Trigon
            else if(this.pw.getText().toString().equals("Admin"))
            {
                Toast.makeText(MainActivity.this, "You are logged in as admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                startActivity(intent);

            }
            //scouter Trigon
            else if (this.pw.getText().toString().equals("Pass"))
            {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                startActivity(intent);

            }
            //scouter for PRIMO!!
            else if (this.pw.getText().toString().equals("amitlaba0") || this.pw.getText().toString().equals("microgali0"))
            {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("level", pw.getText().toString());
                startActivity(intent);

            }

            //wrong password
            else {
                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
