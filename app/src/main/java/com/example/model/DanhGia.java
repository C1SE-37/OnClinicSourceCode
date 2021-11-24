package com.example.model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String idDanhGia;
    private String nhanXet;
    private Float rating;
    private String idNguoiDungDG;

    public DanhGia() {
    }

    public DanhGia(Float rating, String nhanXet, String idNguoiDungDG){
        this.nhanXet = nhanXet;
        this.rating = rating;
        this.idNguoiDungDG = idNguoiDungDG;
    }

    public String getIdDanhGia() {
        return idDanhGia;
    }

    public void setIdDanhGia(String idDanhGia) {
        this.idDanhGia = idDanhGia;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) { this.nhanXet = nhanXet; }

    public Float getRating() { return rating; }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getIdNguoiDungDG() {
        return idNguoiDungDG;
    }

    public void setIdNguoiDungDG(String idNguoiDungDG) {
        this.idNguoiDungDG = idNguoiDungDG;
    }
}
