package com.example.onclinic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;

public class TrangCaNhan extends AppCompatActivity {


    private Button btn_DangXuat;
    private ImageButton edit_birth, edit_mail, edit_address;
    private TextView txt_DoiMK, txtThuDienTu, txtDiaChi, txtNgaySinh, txt_ten, txt_Doi_ttcn;
    private String idNguoiDung;
    private EditText edtTDNS, edtTDEmail,edtTDDC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);
        idNguoiDung = DataLocalManager.getIDNguoiDung();;
        AnhXa();
        getDataUser();
        btnDangxuat();
        btnS();
    }



    //button dang xuat
    private void btnDangxuat() {
        btn_DangXuat = (Button) findViewById(R.id.btn_DangXuat);

        btn_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TrangCaNhan.this);
                    alertDialogBuilder.setMessage("Bạn muốn đăng xuất?");
                            alertDialogBuilder.setPositiveButton("Xác nhận",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(TrangCaNhan.this, LoiChao.class));
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Hủy",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
        });
    }

    private void AnhXa(){
        txt_ten = (TextView) findViewById(R.id.txt_ten);
        txtThuDienTu = findViewById(R.id.txt_Mail);
        txtDiaChi = findViewById(R.id.txt_address);
        txtNgaySinh = findViewById(R.id.txt_ngaysinh);
        txt_Doi_ttcn = findViewById(R.id.txt_Doi_ttcn);

        edit_birth = (ImageButton) findViewById(R.id.btn_edit1);
        edit_mail = (ImageButton) findViewById(R.id.btn_edit3);
        edit_address = (ImageButton) findViewById(R.id.btn_edit4);
        txt_DoiMK = (TextView) findViewById(R.id.txt_DoiMK);
        btn_DangXuat = findViewById(R.id.btn_DangXuat);

        edtTDEmail = findViewById(R.id.edt_thay_doi_mail);

    }

    private void getDataUser(){
        DatabaseReference myRefBN = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BENHNHAN);
        myRefBN.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    NguoiDung nguoiDung = dataSnapshot.getValue(NguoiDung.class);
                    if(idNguoiDung.equals(nguoiDung.getUserID().toString().trim()))
                    {
                        DataLocalManager.setIDNguoiDung(dataSnapshot.getKey());
                        txt_ten.setText(nguoiDung.getTenNguoiDung());
                        txtNgaySinh.setText("Ngày sinh: "+ nguoiDung.getNgaySinh());
                        txtDiaChi.setText("Địa chỉ: "+ nguoiDung.getQuan() +", "+ nguoiDung.getThanhpho());
                        txtThuDienTu.setText("Thư điện tử: "+nguoiDung.getEmail_sdt());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrangCaNhan.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void btnS() {

        //button edit ngaysinh
        edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditNgaySinh(Gravity.CENTER);
            }
        });

        //button edit email
        edit_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditMail(Gravity.CENTER);
            }
        });

        //button edit dia chi
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditDiaChi(Gravity.CENTER);
            }
        });



    }

    private void openEditNgaySinh(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thay_doi_ngay_sinh);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }

        dialog.show();

    }

    private void openEditMail(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thay_doi_mail);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }

        dialog.show();

        Button btnCapNhat = dialog.findViewById(R.id.btnCapNhat);
        EditText edtMail = dialog.findViewById(R.id.edt_thay_doi_mail);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);



        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void openEditDiaChi(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thaydoi_thongtin_canhan);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }

        dialog.show();
    }


}
