package com.example.model;

import java.util.Date;

public class NguoiDung {
    private String tenNguoiDung;
    private String email;
    private String ngaySinh;
    private String thanhpho,quan;

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getThanhpho() {
        return thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        this.thanhpho = thanhpho;
    }

    public NguoiDung(String tenNguoiDung, String email, String ngaySinh, String thanhpho, String quan) {
        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.quan = quan;
        this.thanhpho = thanhpho;
    }

    public NguoiDung() {
    }

}
