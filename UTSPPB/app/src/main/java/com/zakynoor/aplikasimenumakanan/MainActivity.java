package com.zakynoor.aplikasimenumakanan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[];
    int image[] ={R.drawable.ayamlombokijo, R.drawable.sateayam, R.drawable.satekambing, R.drawable.esteh};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =  findViewById(R.id.recyclerview);

        s1 = getResources().getStringArray(R.array.menu_makanan);
        s2 = getResources().getStringArray((R.array.harga));

        MyAdapter myAdapter = new MyAdapter(this, s1, s2, image);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}