package com.example.onclinic;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.local_data.DataLocalManager;
import com.example.sqlhelper.ActivityState;

public class TrangChuBenhNhan extends MyBaseActivity{

    ImageButton ibtnDatPhong, ibtnDoNhipTim, ibtnLichSuKham, ibtnKhamOnline, ibtnLienHe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_trang_chu_benh_nhan,null,false);
        mDrawerLayout.addView(view,0);
        //setContentView(R.layout.activity_trang_chu_benh_nhan);
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
                Intent intent = new Intent(TrangChuBenhNhan.this, LichSuKhamBenhNhan.class);
                startActivity(intent);
            }
        });

        ibtnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChuBenhNhan.this, LienHe.class);
                DataLocalManager.setActivityNumber(ActivityState.ACTIVITY_LIENHE);
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
        ibtnKhamOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuBenhNhan.this,LichKhamBenhNhan.class);
                startActivity(intent);
            }
        });
        ibtnLichSuKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuBenhNhan.this,LichSuKhamBenhNhan.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(TrangChuBenhNhan.this)
                .setTitle("Thông báo").setMessage("Bạn muốn đăng xuất?")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TrangChuBenhNhan.this,LoiChao.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void AnhXa() {
        ibtnDatPhong = findViewById(R.id.ibtnDatPhong);
        ibtnDoNhipTim = findViewById(R.id.ibtnDoNhipTimBN);
        ibtnLichSuKham = findViewById(R.id.ibtnLichSuKhamBN);
        ibtnKhamOnline = findViewById(R.id.ibtnKhamOnlineBN);
        ibtnLienHe = findViewById(R.id.ibtnLienHeBN);
        ibtnLichSuKham = findViewById(R.id.ibtnLichSuKhamBN);
    }
}