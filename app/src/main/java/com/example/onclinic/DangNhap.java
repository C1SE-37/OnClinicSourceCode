package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

        AnhXa();
        addEvents();
        layDanhSachNguoiDungTuFireBase();
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickDangNhap();
                xuLyDangNhap();
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
    }

    private void xuLyQuenMatKhau() {
        Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
        startActivity(intent);
    }

    private void xuLyDangNhap() {
        String strEmail = edtEmailHoacSdt.getText().toString().trim();
        String strMatKhau = edtMatKhau.getText().toString().trim();
        for(NguoiDung nguoiDung : listBenhNhan )
        {
            if(strEmail.equals(nguoiDung.getEmail_sdt()) && strMatKhau.equals(nguoiDung.getMatKhau()))
            {
                DataLocalManager.setIDNguoiDung(nguoiDung.getUserID().toString());//lưu id vào dữ liệu local
                DataLocalManager.setNguoiDung(nguoiDung);
                Intent intent = new Intent(DangNhap.this,TrangChuBenhNhan.class);
                startActivity(intent);
                finishAffinity();
            }
        }
        for(NguoiDung nguoiDung : listBacSi)
        {
            if(strEmail.equals(nguoiDung.getEmail_sdt()) && strMatKhau.equals(nguoiDung.getMatKhau()))
            {
                DataLocalManager.setIDNguoiDung(nguoiDung.getUserID().toString());
                DataLocalManager.setNguoiDung(nguoiDung);
                Intent intent = new Intent(DangNhap.this,TrangChuBacSi.class);
                startActivity(intent);
                finishAffinity();
            }
        }
        Toast.makeText(DangNhap.this, "Email/sdt hoặc mật khẩu đã sai.", Toast.LENGTH_SHORT).show();
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

    private void clickDangNhap() {
        String strEmail = edtEmailHoacSdt.getText().toString().trim();
        String strMatKhau = edtMatKhau.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strMatKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(DangNhap.this, TrangChuBenhNhan.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {

                            Toast.makeText(DangNhap.this, "Email/sdt hoặc mật khẩu đã sai.",
                                    Toast.LENGTH_SHORT).show();
                        }
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