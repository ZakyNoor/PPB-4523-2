package com.zakynoor.myfirebaseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TampilSemuaData extends AppCompatActivity {
    ListView listView;
    ArrayAdapter arrayAdapter;

    ArrayList<String> arrayTampil=new ArrayList<>();
    ArrayList<String> arrayEdit=new ArrayList<>();
    ArrayList<String> arrayHapus=new ArrayList<>();

    DatabaseReference databaseReference;
    public String data1,data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_semua);
        listView=findViewById(R.id.listdataku);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("siswa");
        arrayAdapter= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayTampil);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent=new Intent(TampilSemuaData.this,MainActivity.class);
            startActivity(intent);
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String hasil=snapshot.getValue(Mahasiswa.class).toPrint();
                arrayTampil.add(hasil);
                String key=snapshot.getKey();
                arrayEdit.add(key);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String hasil=snapshot.getValue(Mahasiswa.class).toPrint();
                String key=snapshot.getKey();
                int index=arrayEdit.indexOf(key);
                arrayTampil.set(index,hasil);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String hasil=snapshot.getValue(Mahasiswa.class).toPrint();
                String key=snapshot.getKey();
                int index=arrayHapus.indexOf(key);
                arrayTampil.remove(index);
                arrayHapus.remove(index);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
