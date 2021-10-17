package com.example.model;

public class TaiKhoan {
    private String email_sdt;
    private String matKhau;

    public TaiKhoan() {
    }

    public TaiKhoan(String email_sdt, String matKhau) {
        this.email_sdt = email_sdt;
        this.matKhau = matKhau;
    }

    public String getEmail_sdt() {
        return email_sdt;
    }

    public void setEmail_sdt(String email_sdt) {
        this.email_sdt = email_sdt;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
