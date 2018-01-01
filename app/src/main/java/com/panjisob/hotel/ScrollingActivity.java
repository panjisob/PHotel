package com.panjisob.hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ScrollingActivity extends AppCompatActivity {
    private TextView alamat, harga;
    private String id, nama;
    private ImageView f1, f2, f3, f4, f5;
    private EditText namaP, email, jmlK;
    private Button pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        namaP = (EditText) findViewById(R.id.namaP);
        email = (EditText) findViewById(R.id.email);
        jmlK = (EditText) findViewById(R.id.jml);
        pesan = (Button) findViewById(R.id.pesan);

        id = getIntent().getStringExtra(konfigurasi.EMP_ID);
        nama = getIntent().getStringExtra(konfigurasi.TAG_NAMA);
        setTitle(nama);

        getEmployee();

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEmployee();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action" + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ScrollingActivity.this,"Mengambil Data",
                        "Tunggu Sebentar...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(konfigurasi.URL_GET_EMP,id);
            }
        }
        new GetEmployee().execute();
    }

    private void showEmployee(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            f1 = (ImageView) findViewById(R.id.gambar);
            f2 = (ImageView) findViewById(R.id.f2);
            f3 = (ImageView) findViewById(R.id.f3);
            f4 = (ImageView) findViewById(R.id.f4);
            f5 = (ImageView) findViewById(R.id.f5);
            Glide.with(ScrollingActivity.this).load(c.getString(konfigurasi.TAG_FOTO1)).fitCenter().into(f1);
            Glide.with(ScrollingActivity.this).load(c.getString(konfigurasi.TAG_FOTO2)).fitCenter().into(f2);
            Glide.with(ScrollingActivity.this).load(c.getString(konfigurasi.TAG_FOTO3)).fitCenter().into(f3);
            Glide.with(ScrollingActivity.this).load(c.getString(konfigurasi.TAG_FOTO4)).fitCenter().into(f4);
            Glide.with(ScrollingActivity.this).load(c.getString(konfigurasi.TAG_FOTO5)).fitCenter().into(f5);
            setTitle(c.getString(konfigurasi.TAG_NAMA));
            alamat = (TextView) findViewById(R.id.alamat);
            alamat.setText(c.getString(konfigurasi.TAG_ALAMAT));
            harga = (TextView) findViewById(R.id.harga);
            harga.setText(c.getString(konfigurasi.TAG_HARGA));
            //nama.setText(c.getString(konfigurasi.TAG_NAMA));
//            enama.setText(c.getString(konfigurasi.TAG_NAMA));
//            epos.setText(c.getString(konfigurasi.TAG_POSISI));
//            egaj.setText(c.getString(konfigurasi.TAG_GAJI));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void reset(){

    }

    private void AddEmployee(){
        final String nama = namaP.getText().toString().trim();
        final String em    = email.getText().toString().trim();
        final String jml  = jmlK.getText().toString().trim();
        class AddEmployee extends AsyncTask<Void,Void,String > {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        ScrollingActivity.this,
                        "Menambahkan...",
                        "Tunggu Sebentar...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                reset();
                Toast.makeText(ScrollingActivity.this, s, Toast.LENGTH_LONG).show();
                if (s.equals("Berhasil ditambah")) {
                    Intent intent = new Intent(ScrollingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_NAMA,nama);
                params.put(konfigurasi.KEY_EMP_EAIL,em);
                params.put(konfigurasi.KEY_EMP_ID,id);
                params.put(konfigurasi.KEY_EMP_JMKM,jml);
                RequestHandler requestHandler = new RequestHandler();
                return requestHandler.sendPostRequest(konfigurasi.URL_ADD,params);
            }
        }

        new AddEmployee().execute();
    }
}
