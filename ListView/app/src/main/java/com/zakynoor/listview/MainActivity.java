package com.zakynoor.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    public String bhs_prog[]={"Prolog","C+","Pascal","Cobol","Per1","Algol L","Java","PHP","Phyton"};

    Spinner combo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.listData);
        combo=(Spinner) findViewById(R.id.comboData);

        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this, R.layout.support_simple_spinner_dropdown_item,bhs_prog);
        listView.setAdapter(adapter);
        combo.setAdapter(adapter);
    }
}