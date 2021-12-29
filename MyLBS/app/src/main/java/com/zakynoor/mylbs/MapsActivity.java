package com.zakynoor.mylbs;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zakynoor.mylbs.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    public static final String nama_pulau[] = {"Sumatra","Jawa","Kalimantan","Sulawesi","Bali","NTB","NTT","Maluku","Papua"};
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner=findViewById(R.id.pilihpulau);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,nama_pulau);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        double lati,longi;
        switch (position){
            case  0:
                mMap.clear();
                LatLng aceh = new LatLng(5.6097544, 95.3713716);
                mMap.addMarker(new MarkerOptions().position(aceh).title("Posisiku Sekarang"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(aceh));
                LatLng medan = new LatLng(3.6422756, 98.5294063);
                mMap.addMarker(new MarkerOptions().position(medan).title("Posisiku Sekarang"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(medan));
                LatLng padang = new LatLng(-0.9345808, 100.2511827);
                mMap.addMarker(new MarkerOptions().position(padang).title("Posisiku Sekarang"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(padang));
                LatLng palembang = new LatLng(-2.9549663, 104.6929239);
                mMap.addMarker(new MarkerOptions().position(palembang).title("Posisiku Sekarang"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(palembang));
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            default:
                Toast.makeText(this, "Pilihan Tidak Ada Sorry..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}