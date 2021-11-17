package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewDanhGia extends AppCompatActivity {

    Button VietDanhGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phan_hoi);
        anhXa();
        addEvent();
    }

    private void addEvent(){
        VietDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDanhGia.this,DanhGia.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa(){
        VietDanhGia = findViewById(R.id.btn_VietDanhGia);
    }
}