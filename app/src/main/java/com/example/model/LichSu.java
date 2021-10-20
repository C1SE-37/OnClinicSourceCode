package com.example.model;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.Timer;

public class LichSu {
    private String ngayDatPhong;
    private String ngayKhamBenh;
    private String maPhongKham;
    private String maNguoiKham;
    private String maNguoiDat;
    private String tongTien;

    public LichSu() {
    }

    public LichSu(String ngayDatPhong, String ngayKhamBenh, String maPhongKham, String maNguoiKham, String maNguoiDat, String tongTien) {
        this.ngayDatPhong = ngayDatPhong;
        this.ngayKhamBenh = ngayKhamBenh;
        this.maPhongKham = maPhongKham;
        this.maNguoiKham = maNguoiKham;
        this.maNguoiDat = maNguoiDat;
        this.tongTien = tongTien;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getNgayKhamBenh() {
        return ngayKhamBenh;
    }

    public void setNgayKhamBenh(String ngayKhamBenh) {
        this.ngayKhamBenh = ngayKhamBenh;
    }

    public String getMaPhongKham() {
        return maPhongKham;
    }

    public void setMaPhongKham(String maPhongKham) {
        this.maPhongKham = maPhongKham;
    }

    public String getMaNguoiKham() {
        return maNguoiKham;
    }

    public void setMaNguoiKham(String maNguoiKham) {
        this.maNguoiKham = maNguoiKham;
    }

    public String getMaNguoiDat() {
        return maNguoiDat;
    }

    public void setMaNguoiDat(String maNguoiDat) {
        this.maNguoiDat = maNguoiDat;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
