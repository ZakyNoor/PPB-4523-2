package com.zakynoor.aplikasimenumakanan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    ImageView mainImageView;
    TextView menu, harga;

    String data1,data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mainImageView = findViewById(R.id.foto2);
        menu = findViewById(R.id.nama2);
        harga = findViewById(R.id.harga2);

        getData();
        setData();
    }
    private void  getData(){
        if(getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){
            data1 =getIntent().getStringExtra("data1");
            data2 =getIntent().getStringExtra("data2");
            myImage =getIntent().getIntExtra("myImage",1);
        }else{
            Toast.makeText(this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData(){
        menu.setText(data1);
        harga.setText(data2);
        mainImageView.setImageResource(myImage);
    }

}