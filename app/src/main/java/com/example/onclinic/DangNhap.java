package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    private EditText edtEmailHoacSdt, edtMatKhau;
    private Button btnDangNhap;
    private TextView txtQuenMk;

    private TextView txtDangNhap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        AnhXa();
        xuLyDangNhap();
        xuLyQuenMatKhau();
        xuLyDangKy();
    }

    private void xuLyDangKy() {
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyQuenMatKhau() {
        txtQuenMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, QuenMatKhau.class);
                startActivity(intent);
            }
        });
    }

    private void xuLyDangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDangNhap();
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
    }
}