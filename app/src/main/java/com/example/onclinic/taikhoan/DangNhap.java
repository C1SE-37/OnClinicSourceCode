package com.example.onclinic.taikhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.onclinic.LienHe;
import com.example.onclinic.R;
import com.example.onclinic.TrangChuBacSi;
import com.example.onclinic.TrangChuBenhNhan;
import com.example.sqlhelper.ActivityState;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DangNhap extends AppCompatActivity {

    private EditText edtEmailHoacSdt, edtMatKhau;
    private Button btnDangNhap;
    private TextView txtQuenMk;

    private TextView txtDangNhap;
    private ProgressDialog progressDialog;

    ArrayList<NguoiDung> listBenhNhan = new ArrayList<>();
    ArrayList<NguoiDung> listBacSi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        layDanhSachNguoiDungTuFireBase();
        AnhXa();
        addEvents();
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(xuLyDangNhap()==-1)
                    Toast.makeText(DangNhap.this, "Email/sdt hoặc mật khẩu đã sai.", Toast.LENGTH_SHORT).show();
            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangKy();
            }
        });
        txtQuenMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyQuenMatKhau();
            }
        });
    }

    private void xuLyDangKy() {
        Intent intent = new Intent(DangNhap.this, DangKy.class);
        startActivity(intent);
        finish();
    }

    private void xuLyQuenMatKhau() {
        Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
        startActivity(intent);
    }

    private int xuLyDangNhap() {
        String strEmail = edtEmailHoacSdt.getText().toString().trim();
        String strMatKhau = edtMatKhau.getText().toString().trim();
        for(NguoiDung nguoiDung : listBenhNhan )
        {
            if(strEmail.equals(nguoiDung.getEmail_sdt()) && strMatKhau.equals(nguoiDung.getMatKhau()))
            {
                DataLocalManager.setIDNguoiDung(nguoiDung.getUserID().toString());//lưu id vào dữ liệu local
                DataLocalManager.setNguoiDung(nguoiDung);//lưu người dùng vào dữ liệu local
                DataLocalManager.setRole(0);
                //DataLocalManager.setActivityNumber(ActivityState.ACTIVITY_TRANGCHU);
                Intent intent = new Intent(DangNhap.this, TrangChuBenhNhan.class);
                startActivity(intent);
                return 0;
            }
        }
        for(NguoiDung nguoiDung : listBacSi)
        {
            if(strEmail.equals(nguoiDung.getEmail_sdt()) && strMatKhau.equals(nguoiDung.getMatKhau()))
            {
                DataLocalManager.setIDNguoiDung(nguoiDung.getUserID().toString());//lưu id vào dữ liệu local
                DataLocalManager.setNguoiDung(nguoiDung);//lưu người dùng vào dữ liệu local
                DataLocalManager.setRole(1);
                //DataLocalManager.setActivityNumber(ActivityState.ACTIVITY_TRANGCHU);
                Intent intent = new Intent(DangNhap.this, TrangChuBacSi.class);
                startActivity(intent);
                return 1;
            }
        }
        return -1;
    }

    private void layDanhSachNguoiDungTuFireBase()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference();//đang ở note roof
        DatabaseReference refBenhNhan = myRef.child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BENHNHAN);//đến note bệnh nhân
        refBenhNhan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBenhNhan.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    NguoiDung nguoiDung = data.getValue(NguoiDung.class);
                    listBenhNhan.add(nguoiDung);//them benh nhan tu fb vao danh sach
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhap.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference refBacSi = myRef.child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BACSI);
        refBacSi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBacSi.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    NguoiDung nguoiDung = data.getValue(NguoiDung.class);
                    listBacSi.add(nguoiDung);//them bac si tu fb vao danh sach
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhap.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        edtEmailHoacSdt = findViewById(R.id.edtEmailHoacSdt);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        txtQuenMk = findViewById(R.id.txtQuenMK);

        txtDangNhap = findViewById(R.id.txtDangNhap);
        progressDialog = new ProgressDialog(this);
        listBenhNhan = new ArrayList<NguoiDung>();
        listBacSi = new ArrayList<NguoiDung>();
    }
}