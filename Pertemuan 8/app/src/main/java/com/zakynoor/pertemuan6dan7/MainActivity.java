package com.zakynoor.pertemuan6dan7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BantuDatabase bd;
    public static ListView listView;
    public static EditText editText;
    Button tblTambah;

    ArrayAdapter adapter;
    ArrayList<String> listviewku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listdatabuah);
        editText=findViewById(R.id.databuah);
        tblTambah=findViewById(R.id.tbltambah);
        listviewku=new ArrayList<>();
        tampilkan_buah();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int position, long l) {
                final String noid=listviewku.get(position);
                final String nomor=noid.substring(0,2);
                //editText.setText(nomor.toString());

                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Perhatian !")
                    .setMessage("Yakin Menghpus data ini?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            bd.hapusRecord(Integer.parseInt(nomor));
                            listviewku.remove(position);
                            listView.invalidateViews();
                        }
                    })
                        .setNegativeButton("No",null)
                        .show();
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String noid= listviewku.get(position).toString();
                String nomor= noid.substring(0,2);
                String nama= listviewku.get(position).toString();
                String namae= nama.substring(1,10);

                if(nama !=null && nama!= ""){
                    Intent intent = new Intent(MainActivity.this, UpdateData.class);
                    intent.putExtra("data1", nomor);
                    intent.putExtra("data2", nama);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(),"Data Masih Kosong !", Toast.LENGTH_SHORT);
                }
            }
        });

        tblTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd.tambahData(editText.getText().toString());
                Toast.makeText(MainActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                tampilkan_buah();
            }
        });
    }

    private void tampilkan_buah() {
        Cursor cursor = bd.tampilBuah();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Record Kosong Coy !", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext()){
                listviewku.add(String.valueOf(cursor.getInt(0))+" "+cursor.getString(1));
            }
            adapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,listviewku);
            listView.setAdapter(adapter );
        }
    }
}
