package com.zakynoor.aplikasimenumakanan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    ImageView imageView;
    TextView nama,harga;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        imageView = findViewById(R.id.foto2);
        nama = findViewById(R.id.nama2);
        harga = findViewById(R.id.harga2);

        if (position == 0) {
            Intent intent = getIntent();

            Bundle bundle = this.getIntent().getExtras();
            int pic = bundle.getInt("foto");
            String menu = intent.getStringExtra("nama");
            String hargaMakanan = intent.getStringExtra("harga");

            imageView.setImageResource(pic);
            nama.setText(menu);
            harga.setText(hargaMakanan);

            actionBar.setTitle(menu);
        }
        if (position == 1) {
            Intent intent = getIntent();

            Bundle bundle = this.getIntent().getExtras();
            int pic = bundle.getInt("foto");
            String menu = intent.getStringExtra("nama");
            String hargaMakanan = intent.getStringExtra("harga");

            imageView.setImageResource(pic);
            nama.setText(menu);
            harga.setText(hargaMakanan);

            actionBar.setTitle(menu);
        }
        if (position == 2) {
            Intent intent = getIntent();

            Bundle bundle = this.getIntent().getExtras();
            int pic = bundle.getInt("foto");
            String menu = intent.getStringExtra("nama");
            String hargaMakanan = intent.getStringExtra("harga");

            imageView.setImageResource(pic);
            nama.setText(menu);
            harga.setText(hargaMakanan);

            actionBar.setTitle(menu);
        }
    }
}