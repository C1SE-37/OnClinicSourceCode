package com.example.model;

import java.io.Serializable;

public class LichKham implements Serializable {
    private String ngayKham;//note chính
    private String gioKham;
    private String hinhThucKham;
    private String idBenhNhan;//người đã đặt lịch
    private String idLichKham;

    public LichKham() {
    }

    public LichKham(String ngayKham, String gioKham) {
        this.ngayKham = ngayKham;
        this.gioKham = gioKham;
    }

    public LichKham(String ngayKham, String gioKham, String hinhThucKham) {
        this.ngayKham = ngayKham;
        this.gioKham = gioKham;
        this.hinhThucKham = hinhThucKham;
    }

    public String getIdLichKham() {
        return idLichKham;
    }

    public void setIdLichKham(String idLichKham) {
        this.idLichKham = idLichKham;
    }

    public String getHinhThucKham() {
        return hinhThucKham;
    }

    public void setHinhThucKham(String hinhThucKham) {
        this.hinhThucKham = hinhThucKham;
    }

    public String getNgayKham() {
        return ngayKham;
    }

    public void setNgayKham(String ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getGioKham() {
        return gioKham;
    }

    public void setGioKham(String gioKham) {
        this.gioKham = gioKham;
    }

    public String getIdBenhNhan() {
        return idBenhNhan;
    }

    public void setIdBenhNhan(String idBenhNhan) {
        this.idBenhNhan = idBenhNhan;
    }
}
