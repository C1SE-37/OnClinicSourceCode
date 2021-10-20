package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DanhGia extends AppCompatActivity {
    EditText edt_BinhLuan;
    Button bnt_SubmitDanhGia;
    RatingBar rating_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        AnhXa();
    }



    private void AnhXa(){
        rating_bar = findViewById(R.id.rating_bar);
        edt_BinhLuan = findViewById(R.id.edt_BinhLuan);
        bnt_SubmitDanhGia = findViewById(R.id.btn_SubmitDanhGia);
    }
}