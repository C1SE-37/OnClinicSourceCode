package com.example.onclinic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class TaoPhongKham extends AppCompatActivity {

    EditText edtTenPhongKham,edtChuyenKhoa,edtDiaChi,edtMoTa;
    CheckBox chkOnline,chkTrucTiep;
    TextView btnChonAnh;
    ImageView imgAnh;
    Bitmap selectedBitmap;
    Button btnHuy, btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_phong_kham);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonAnh();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXacNhan();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyHuy();
            }
        });
    }

    private void xuLyChonAnh() {
        Intent chonPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(chonPhoto,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 200)
        {
            try {
                //xử lí lấy ảnh từ điện thoại
                Uri imageUri = data.getData();
                selectedBitmap = MediaStore.Images.Media.getBitmap(TaoPhongKham.this.getContentResolver(),imageUri);
                imgAnh.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void xuLyXacNhan() {

    }

    private void xuLyHuy() {
        AlertDialog alertDialog = new AlertDialog.Builder(TaoPhongKham.this)
                .setTitle("Thông báo").setMessage("Bạn muốn Hủy tạo phòng khám?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(TaoPhongKham.this,TrangChuBenhNhan.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void addControls() {
        edtTenPhongKham = findViewById(R.id.edtTenPhongKham);
        edtChuyenKhoa = findViewById(R.id.edtChuyenKhoa);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        edtMoTa = findViewById(R.id.edtMoTa);
        chkOnline = findViewById(R.id.chkOnline);
        chkTrucTiep = findViewById(R.id.chkTrucTiep);
        btnChonAnh = findViewById(R.id.btnChonAnh);
        imgAnh = findViewById(R.id.imgPhongKham);
        btnHuy = findViewById(R.id.btnHuyPK);
        btnXacNhan = findViewById(R.id.btnXacNhan);
    }
}