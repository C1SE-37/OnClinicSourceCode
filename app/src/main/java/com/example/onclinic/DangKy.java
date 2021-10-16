package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sqlhelper.CheckData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;


public class DangKy extends AppCompatActivity {

    private EditText edtEmailHoacSdt, edtTen,edtMatKhau, edtNLMatKhau,edtNgay,edtThang, edtNam;
    private Spinner spnQuan, spnThanhPho;
    private Button btnDangKy;
    private TextView txtDangNhap;
    //private ConstraintLayout conStraintDangNhap;
    private ProgressDialog progressDialog;

    int lastSelected = -1;
    ArrayList<Integer> dsNgay,dsThang,dsNam;
    ArrayAdapter<Integer> adapterNgay,adapterThang,adapterNam;
    ArrayList<String>dsQuan,dsThanhPho;
    ArrayAdapter<String>adapterQuan,adapterThanhPho;
    FirebaseAuth auth;
    private String email,matkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        AnhXa();
        addEvents();
    }

    private void clickDangKy() {
        String strEmail = edtEmailHoacSdt.getText().toString().trim();
        String strMatKhau = edtMatKhau.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strMatKhau)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(DangKy.this, DangNhap.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {

                            Toast.makeText(DangKy.this, "Đăng ký không thành công.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void addEvents() {
        spnThanhPho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                    {
                        HienThiQuanHuyen(R.array.quanAnGiang);
                        break;
                    }
                    case 1:
                    {
                        HienThiQuanHuyen(R.array.quanBaRiaVungTau);
                        break;
                    }
                    case 2:
                    {
                        HienThiQuanHuyen(R.array.quanBacGiang);
                        break;
                    }

                    case 14:
                    {
                        HienThiQuanHuyen(R.array.quanDaNang);
                        break;
                    }

                    case 55:
                    {
                        HienThiQuanHuyen(R.array.quanTTHue);
                        break;
                    }
                    default:
                        adapterQuan = null;
                        break;

                }
                lastSelected = position;
                spnQuan.setAdapter(adapterQuan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDangKy();
            }
        });
    }

    private void xuLyDangKy() {
        if (checkInput()==false)
        {
            try
            {
                String strEmail = edtEmailHoacSdt.getText().toString().trim();
                String strMatKhau = edtMatKhau.getText().toString().trim();
                auth.createUserWithEmailAndPassword(strEmail,strMatKhau)
                        .addOnCompleteListener(DangKy.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DangKy.this,DangNhap.class));
                                    finish();
                                }
                                else Toast.makeText(DangKy.this, "Lỗi đăng ký tài khoản", Toast.LENGTH_LONG).show();
                            }
                        });

            }
            catch (Exception ex)
            {
                Toast.makeText(DangKy.this, "Lỗi đăng ký tài khoản", Toast.LENGTH_LONG).show();
            }
        }
        else Toast.makeText(DangKy.this, "Hãy hoàn thành thông tin đăng ký", Toast.LENGTH_LONG).show();
    }

    private boolean checkInput() {
        //kiểm tra dữ liệu nhập vào
        CheckData.isEmpty(edtTen);
        CheckData.isEmpty(edtEmailHoacSdt);
        if(CheckData.isEmpty(edtMatKhau))
        {
            email = edtEmailHoacSdt.getText().toString().trim();
            matkhau = edtMatKhau.getText().toString().trim();
            if(matkhau.length()<6)
            {
                edtMatKhau.requestFocus();
                edtMatKhau.setError("Mật khẩu quá ngắn");
                return true;
            }
        }
        else if(CheckData.isEmpty(edtNLMatKhau))
        {
            if(edtNLMatKhau.equals(edtMatKhau)==false) {
                edtNLMatKhau.requestFocus();
                edtNLMatKhau.setError("Mật khẩu không giống");
            }
        }
        return false;
    }

    private void HienThiQuanHuyen(int position) {
        dsQuan = new ArrayList<>();
        dsQuan.addAll(Arrays.asList(getResources().getStringArray(position)));
        adapterQuan = new ArrayAdapter<String>(
                DangKy.this, android.R.layout.simple_spinner_item, dsQuan);
    }

    private void AnhXa() {
        edtEmailHoacSdt = findViewById(R.id.edtEmailHoacSdt);
        edtTen = findViewById(R.id.edtTen);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNLMatKhau = findViewById(R.id.edtNLMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        txtDangNhap = findViewById(R.id.txtDangNhap);
        progressDialog = new ProgressDialog(this);

        edtNgay = findViewById(R.id.edtNgay);
        edtThang = findViewById(R.id.edtThang);
        edtNam = findViewById(R.id.edtNam);
        spnQuan = findViewById(R.id.spnQuan);
        spnThanhPho = findViewById(R.id.spnThanhPho);
        dsThanhPho = new ArrayList<>();
        dsThanhPho.addAll(Arrays.asList(getResources().getStringArray(R.array.arrThanhPho)));
        adapterThanhPho = new ArrayAdapter<String>(
                DangKy.this, android.R.layout.simple_spinner_item, dsThanhPho
        );
        adapterThanhPho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnThanhPho.setAdapter(adapterThanhPho);
    }
}