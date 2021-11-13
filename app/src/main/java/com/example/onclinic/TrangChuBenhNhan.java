package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

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
    }

    private void AnhXa() {
        ibtnDatPhong = findViewById(R.id.ibtnDatPhong);
        ibtnDoNhipTim = findViewById(R.id.ibtnDoNhipTimBN);
        ibtnLichSuKham = findViewById(R.id.ibtnLichSuKhamBN);
        ibtnKhamOnline = findViewById(R.id.ibtnKhamOnlineBN);
        ibtnLienHe = findViewById(R.id.ibtnLienHeBN);
    }
}