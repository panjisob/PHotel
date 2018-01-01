package com.panjisob.hotel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by panji on 18/12/17.
 */

public class HoteList extends ArrayAdapter<Hotel> {

    private Activity contaxt;
    private List<Hotel> hotelList;

    public HoteList(Activity contaxt, List<Hotel> hotelList){

        super(contaxt, R.layout.list_item, hotelList);
        this.contaxt = contaxt;
        this.hotelList = hotelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = contaxt.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);

        TextView textViewId = (TextView) listViewItem.findViewById(R.id.id);
        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.name);
        TextView textViewHarga = (TextView) listViewItem.findViewById(R.id.harga);
        //TextView textViewFoto = (TextView) listViewItem.findViewById(R.id.foto);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.gambar);

        Hotel hotel = hotelList.get(position);
        textViewId.setText(hotel.getId());
        textViewNama.setText(hotel.getNama());
        textViewHarga.setText(hotel.getHarga());
        //textViewFoto.setText(hotel.getFoto());
        Glide.with(contaxt).load(hotel.getFoto()).into(imageView);

        return listViewItem;
    }
}
