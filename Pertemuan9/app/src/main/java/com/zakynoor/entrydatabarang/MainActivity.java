package com.zakynoor.entrydatabarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView) findViewById(R.id.listdata);

        tampilkanbarang("http://localhost/service_data.php");
    }

    private void tampilkanbarang(final String urlWeb) {
        class tampilkanbarang extends AsyncTask<Void,Void,String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), "s", Toast.LENGTH_SHORT).show();
                try {
                    bacadatakelist(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try{
                    URL url=new URL(urlWeb);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));

                    String json;
                    while ((json=bufferedReader.readLine()) != null)
                    {
                        sb.append(json + "/n");
                    }
                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        tampilkanbarang tampil= new tampilkanbarang();
        tampil.execute();
    }

    private void bacadatakelist(String json) throws JSONException {
        JSONArray jsonArray=new JSONArray(json);
        String[] stok=new String[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++)
        {
            JSONObject obj=jsonArray.getJSONObject(i);
            stok[i]=obj.getString("kode")+" "+
                    obj.getString("nama_data")+" "+
                    obj.getString("harga");
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<~>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
    }
}