package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TrangChuBacSi extends AppCompatActivity {

    ImageButton btnQuanly;
    ImageButton btnLichKham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_bac_si);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnQuanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuBacSi.this,QuanLyPhongKham.class);
                startActivity(intent);
            }
        });
        btnLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuBacSi.this,LichKhamBacSi.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        btnQuanly = findViewById(R.id.ibtnQuanLyPhongKham);
        btnLichKham = findViewById(R.id.ibtnLichKham);
    }
}