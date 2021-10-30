package com.example.model;

public class LichKham {
    private String ngayKham;//note chính
    private String gioKham;
    private String idBenhNhan;//người đã đặt lịch

    public LichKham() {
    }

    public LichKham(String ngayKham, String gioKham) {
        this.ngayKham = ngayKham;
        this.gioKham = gioKham;
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
