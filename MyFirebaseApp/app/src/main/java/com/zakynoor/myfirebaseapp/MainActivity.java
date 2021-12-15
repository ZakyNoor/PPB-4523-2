package com.zakynoor.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText nim,nama;
    Button tblAdd, tblView;
    DatabaseReference reference;
    Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        nim=findViewById(R.id.editNim);
        nama=findViewById(R.id.editNama);
        tblAdd=findViewById(R.id.tombolAdd);
        tblView=findViewById(R.id.tombolView);
        mahasiswa=new Mahasiswa();

        reference=FirebaseDatabase.getInstance().getReference().child("siswa");

        tblAdd.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                mahasiswa.setNim(nim.getText().toString().trim());
                mahasiswa.setNama(nama.getText().toString().trim());
                reference.push().setValue(mahasiswa);
                Toast.makeText(MainActivity.this, "Data Tersimpan", Toast.LENGTH_LONG).show();
            }
        });
        tblView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TampilSemuaData.class);
                startActivity(intent);
            }
        });

    }
}