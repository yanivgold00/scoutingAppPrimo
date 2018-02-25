package com.a4586.primo.primoscoutingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {

    private EditText pw;
    private EditText name;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pw = (EditText) findViewById(R.id.pw);
        name = (EditText) findViewById(R.id.Name);
        loginBtn = (Button) findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBtn.getId()) {
//            if (this.pw.getText().toString().equals("microgali0")) {
            if (this.pw.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("isAdmin", false);
                startActivity(intent);

            } else if (this.pw.getText().toString().equals("admin")) {
                Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ScoutingChooseActivity.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("isAdmin", true);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
