package com.example.model;

public class DonThuoc {
    String tenThuoc, lieuDung;
    int soLuong;
    int donGia;

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getLieuDung() {
        return lieuDung;
    }

    public void setLieuDung(String lieuDung) {
        this.lieuDung = lieuDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public DonThuoc(String tenThuoc, String lieuDung, int soLuong, int donGia) {
        this.tenThuoc = tenThuoc;
        this.lieuDung = lieuDung;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    @Override
    public String toString() {
        return "DonThuoc{" +
                "tenThuoc='" + tenThuoc + '\'' +
                ", lieuDung='" + lieuDung + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}
