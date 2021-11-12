package com.zakynoor.aplikasimenumakanan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String rMenu[] = {"Pecel Lele","Mie Ayam","Nasi Goreng"};
    String rHargaMakanan[] = {"Rp.17.000,-","Rp.13.000,-","Rp.10.000,-"};
    int rFotoMakanan[] = {R.drawable.pecellele,R.drawable.mieayam,R.drawable.nasigoreng};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listmakanan);

        MyAdapter adapter = new MyAdapter(this, rMenu, rHargaMakanan, rFotoMakanan);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    //Memasukkan list fotomakanan ke jendela baru
                    Bundle bundle = new Bundle();
                    bundle.putInt("foto", rFotoMakanan[0]);
                    //Memasukkan list menu dan harga ke jendela baru
                    intent.putExtras(bundle);
                    intent.putExtra("nama", rMenu[0]);
                    intent.putExtra("harga", rHargaMakanan[0]);
                    //Memberi nilai posisi
                    intent.putExtra("position", +0);
                    startActivity(intent);

                }
                if(position==1){
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("foto", rFotoMakanan[1]);
                    intent.putExtras(bundle);
                    intent.putExtra("nama", rMenu[1]);
                    intent.putExtra("harga", rHargaMakanan[1]);

                    intent.putExtra("position", +1);
                    startActivity(intent);
                }
                if(position==2) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("foto", rFotoMakanan[2]);
                    intent.putExtras(bundle);
                    intent.putExtra("nama", rMenu[2]);
                    intent.putExtra("harga", rHargaMakanan[2]);
                    intent.putExtra("position", +2);
                    startActivity(intent);
                }
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String rMenu[];
        String rHargaMakanan[];
        int rFotoMakanan[];

        MyAdapter (Context c, String menu[], String hargaMakanan[], int fotoMakanan[]){
            super(c, R.layout.makanan, R.id.nama, menu);
            this.context = c;
            this.rMenu = menu;
            this.rHargaMakanan = hargaMakanan;
            this.rFotoMakanan = fotoMakanan;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View makanan = layoutInflater.inflate(R.layout.makanan, parent ,false);
            ImageView fotoMakanan = makanan.findViewById(R.id.foto);
            TextView  menu = makanan.findViewById(R.id.nama);
            TextView hargaMakanan = makanan.findViewById(R.id.harga);

            fotoMakanan.setImageResource(rFotoMakanan[position]);
            menu.setText(rMenu[position]);
            hargaMakanan.setText(rHargaMakanan[position]);

            return makanan;
        }
    }
}