package com.example.model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String idDanhGia;
    private String nhanXet;
    private Float rating;
    private String idNguoiDungDG;
    private String tenNguoiDungDG;
    private String avatarNguoiDungDG;

    public DanhGia() {
    }

    public DanhGia(Float rating, String nhanXet, String tenNguoiDungDG, String avatarNguoiDungDG, String idNguoiDungDG){
        this.nhanXet = nhanXet;
        this.rating = rating;
        this.tenNguoiDungDG = tenNguoiDungDG;
        this.avatarNguoiDungDG = avatarNguoiDungDG;
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

    public String getTenNguoiDungDG() {
        return tenNguoiDungDG;
    }

    public void setTenNguoiDungDGString (String tenNguoiDungDG) { this.tenNguoiDungDG = tenNguoiDungDG; }

    public String getAvatarNguoiDungDG() {
        return avatarNguoiDungDG;
    }

    public void setAvatarNguoiDungDG(String avatarNguoiDungDG) { this.avatarNguoiDungDG = avatarNguoiDungDG; }


}
