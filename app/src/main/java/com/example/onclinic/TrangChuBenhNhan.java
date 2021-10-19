package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TrangChuBenhNhan extends AppCompatActivity {

    ImageButton ibtnDatPhong, ibtnDoNhipTim, ibtnLichSuKham, ibtnKhamOnline, ibtnLienHe,
            ibtnThongBao, ibtnTrangCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_benh_nhan);

        AnhXa();
        XyLy();
    }

    private void XyLy() {
        ibtnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, DatPhong.class);
                startActivity(intent);
            }
        });

        ibtnLichSuKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, LichSuKham.class);
                startActivity(intent);
            }
        });

        ibtnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, LienHe.class);
                startActivity(intent);
            }
        });

        ibtnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, DatPhong.class);
                startActivity(intent);
            }
        });

        ibtnThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, ThongBao.class);
                startActivity(intent);
            }
        });

        ibtnTrangCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, TrangCaNhan.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        ibtnDatPhong = findViewById(R.id.ibtnDatPhong);
        ibtnDoNhipTim = findViewById(R.id.ibtnDoNhipTim);
        ibtnLichSuKham = findViewById(R.id.ibtnLichSuKham);
        ibtnKhamOnline = findViewById(R.id.ibtnKhamOnline);
        ibtnLienHe = findViewById(R.id.ibtnLienHe);
        ibtnThongBao = findViewById(R.id.ibtnThongBao);
        ibtnTrangCaNhan = findViewById(R.id.ibtnTrangCaNhan);
    }
}