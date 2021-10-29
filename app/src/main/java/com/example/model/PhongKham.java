package com.example.model;

import java.util.Date;

public class PhongKham {
    private String idPhongKham;
    private String tenPhongKham;
    private String chuyenKhoa;
    private String diaChi;
    private String moTa;
    private String hinhAnh;
    private String hinhThucKham;
    private String lichKham;
    private String idBacSi;

    public PhongKham() {
    }

    public PhongKham(String tenPhongKham, String chuyenKhoa, String diaChi, String moTa, String hinhAnh, String hinhThucKham, String lichKham) {
        this.tenPhongKham = tenPhongKham;
        this.chuyenKhoa = chuyenKhoa;
        this.diaChi = diaChi;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.hinhThucKham = hinhThucKham;
        this.lichKham = lichKham;
    }

    public String getLichKham() {
        return lichKham;
    }

    public void setLichKham(String lichKham) {
        this.lichKham = lichKham;
    }

    public String getIdPhongKham() {
        return idPhongKham;
    }

    public void setIdPhongKham(String idPhongKham) {
        this.idPhongKham = idPhongKham;
    }

    public String getIdBacSi() {
        return idBacSi;
    }

    public void setIdBacSi(String idBacSi) {
        this.idBacSi = idBacSi;
    }

    public String getTenPhongKham() {
        return tenPhongKham;
    }

    public void setTenPhongKham(String tenPhongKham) {
        this.tenPhongKham = tenPhongKham;
    }

    public String getChuyenKhoa() {
        return chuyenKhoa;
    }

    public void setChuyenKhoa(String chuyenKhoa) {
        this.chuyenKhoa = chuyenKhoa;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getHinhThucKham() {
        return hinhThucKham;
    }

    public void setHinhThucKham(String hinhThucKham) {
        this.hinhThucKham = hinhThucKham;
    }
}
