package com.panjisob.hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("Pesanan");

        listView = (ListView) findViewById(R.id.listPesan);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Main2Activity.this,TampilPesan.class);
                HashMap<String,String> map = (HashMap)adapterView.getItemAtPosition(i);
                intent.putExtra(konfigurasi.EMP_ID,map.get(konfigurasi.TAG_ID).toString());
                startActivity(intent);
                String id = map.get(konfigurasi.TAG_ID).toString();
                Toast.makeText(Main2Activity.this, "id = " + id, Toast.LENGTH_LONG).show();
                System.out.println("id = "+id);
            }
        });
        getJSON();
    }

    private void showEmployee(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length();i++){

                JSONObject object = result.getJSONObject(i);
                String nama = object.getString(konfigurasi.TAG_NAMAP);
                String email = object.getString(konfigurasi.TAG_EMAIL);

                HashMap<String,String> employees = new HashMap<>();
                employees.put(konfigurasi.TAG_NAMAP,nama);
                employees.put(konfigurasi.TAG_EMAIL,email);
                //Hotel hotel = new Hotel(id,name,harga,foto);
                list.add(employees);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(Main2Activity.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(Main2Activity.this)
                    .setMessage(e.getMessage())
                    .show();
        }

        ListAdapter adapter = new SimpleAdapter(
                Main2Activity.this,list,R.layout.list_pesan,
                new String[] {konfigurasi.TAG_NAMAP,konfigurasi.TAG_EMAIL},
                new int[] {R.id.nama,R.id.email});
        //HoteList adapter = new HoteList(Main2Activity.this,HotelList);
        listView.setAdapter(adapter);
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        Main2Activity.this,
                        "Mengambil Data",
                        "Mohon tunggu...",
                        false,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                String h =rh.sendGetRequest(konfigurasi.URL_GET_PSAN,Main2Activity.this);
                return h;
            }
        }

        GetJSON js = new GetJSON();
        js.execute();
    }

}
