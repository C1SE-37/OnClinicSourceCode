package com.example.model;

import com.example.sqlhelper.NoteFireBase;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PhongKham implements Serializable {
    private String idPhongKham;
    private String tenPhongKham;
    private String chuyenKhoa;
    private String diaChi;
    private String moTa;
    private String hinhAnh;
    private String hinhThucKham;
    private String idBacSi;
    private Float tbDanhGia;

    public PhongKham() {
    }

    public PhongKham(String tenPhongKham, String chuyenKhoa, String diaChi, String moTa, String hinhAnh, String hinhThucKham, Float tbDanhGia) {
        this.tenPhongKham = tenPhongKham;
        this.chuyenKhoa = chuyenKhoa;
        this.diaChi = diaChi;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.hinhThucKham = hinhThucKham;
        this.tbDanhGia = tbDanhGia;
    }

    //sắp xếp phòng khám theo sao giảm dần
    public static Comparator<PhongKham> PhongKhamDescendingStarComparator = new Comparator<PhongKham>() {
        @Override
        public int compare(PhongKham p1, PhongKham p2) {
            //Float f1 = p1.getTbDanhGia().floatValue();
            //Float f2 = p2.getTbDanhGia().floatValue();
            return (int) (p1.getTbDanhGia() - p2.getTbDanhGia());
        }
    };

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

    public Float getTbDanhGia() {
        return tbDanhGia;
    }

    public void setTbDanhGia(Float tbDanhGia) {
        this.tbDanhGia = tbDanhGia;
    }
}
