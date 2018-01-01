package com.panjisob.hotel;

/**
 * Created by panji on 18/12/17.
 */

public class Hotel {
    String id;
    String nama;
    String harga;
    String foto;

    public Hotel(String id, String nama, String harga, String foto) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getFoto() {
        return foto;
    }
}
