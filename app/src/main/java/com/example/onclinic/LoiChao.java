package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoiChao extends AppCompatActivity {

    Button btnDangNhap, btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loichao);

        btnDangKy = (Button) findViewById(R.id.btnDangky);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDN = new Intent(LoiChao.this,DangNhap.class );
                startActivity(intentDN);
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDK = new Intent(LoiChao.this, DangKy.class);
                startActivity(intentDK);
            }
        });
    }


}