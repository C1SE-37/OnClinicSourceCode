package com.example.model;

public class TaiKhoan {
    private String email_sdt;
    private String matKhau;
    private boolean vaiTro;

    public TaiKhoan() {
    }

    public TaiKhoan(String email_sdt, String matKhau, boolean vaiTro) {
        this.email_sdt = email_sdt;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
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

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }
}
