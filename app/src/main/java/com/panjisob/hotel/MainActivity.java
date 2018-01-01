package com.panjisob.hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import layout.ListHotel;

public class MainActivity extends AppCompatActivity{

    ListView listView;
    private String JSON_STRING;
    List<Hotel> HotelList;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            FragmentManager f = getSupportFragmentManager();
//            FragmentTransaction transaction = f.beginTransaction();
//
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    getJSON();
//                    //mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    //transaction.replace(R.id.content, new ListHotel()).commit();
//                    return true;
////                case R.id.navigation_notifications:
////                    mTextMessage.setText(R.string.title_notifications);
////                    return true;
//            }
//            return false;
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //HotelList = new ArrayList<>();

//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hotel hotel = HotelList.get(i);
                //Toast.makeText(MainActivity.this, "id = " + hotel.getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,ScrollingActivity.class);
                intent.putExtra(konfigurasi.EMP_ID,hotel.getId());
                intent.putExtra(konfigurasi.TAG_NAMA,hotel.getNama());
                startActivity(intent);
            }
        });

        getJSON();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });
    }
    private void showEmployee(){

        JSONObject jsonObject;
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            HotelList = new ArrayList<>();

            for (int i = 0; i < result.length();i++){

                JSONObject object = result.getJSONObject(i);
                String id = object.getString(konfigurasi.TAG_ID);
                String name = object.getString(konfigurasi.TAG_NAMA);
                String harga = object.getString(konfigurasi.TAG_HARGA);
                String foto = object.getString(konfigurasi.TAG_FOTO);

//                HashMap<String,String> employees = new HashMap<>();
//                employees.put(konfigurasi.TAG_ID,id);
//                employees.put(konfigurasi.TAG_NAMA,name);
//                employees.put(konfigurasi.TAG_HARGA, harga);
//                employees.put(konfigurasi.TAG_HARGA, foto);
                Hotel hotel = new Hotel(id,name,harga,foto);
                HotelList.add(hotel);
            }
        } catch (JSONException e) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(e.getMessage())
                    .show();
        }catch (NullPointerException e){
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(e.getMessage())
                    .show();
        }

//        ListAdapter adapter = new SimpleAdapter(
//                MainActivity.this,list,R.layout.list_item,
//                new String[] {konfigurasi.TAG_ID,konfigurasi.TAG_NAMA,konfigurasi.TAG_HARGA,konfigurasi.TAG_FOTO},
//                new int[] {R.id.id,R.id.name,R.id.harga,R.id.foto});
        HoteList adapter = new HoteList(MainActivity.this,HotelList);
        listView.setAdapter(adapter);
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(
                        MainActivity.this,
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
                String s =rh.sendGetRequest(konfigurasi.URL_GET_ALL,MainActivity.this);
                return s;
            }
        }

        GetJSON js = new GetJSON();
        js.execute();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
