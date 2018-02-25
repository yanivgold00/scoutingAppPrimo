package com.primo.primoscoutingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends AppCompatActivity {

    ListView lv;
    String[] arr;
    readData rd = new readData();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        arr = new String[1];
        arr[0] = rd.getData();

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr);

        lv = (ListView)findViewById(R.id.resultListView);
        lv.setAdapter(adapter);

    }
}
