package com.example.onclinic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TrangCaNhan extends AppCompatActivity {


    private Button btn_DangXuat;
    private TextView txtThuDienTu, txtDiaChi, txtNgaySinh, txt_ten;
    private String idNguoiDung;
    private TextView txt_DoiMK, txt_Doi_ttcn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);
        idNguoiDung = DataLocalManager.getIDNguoiDung();
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

        txt_DoiMK = (TextView) findViewById(R.id.txt_DoiMK);
        txt_Doi_ttcn = findViewById(R.id.txt_Doi_ttcn);

        btn_DangXuat = findViewById(R.id.btn_DangXuat);
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

        //button edit thông tin cá nhân
        txt_Doi_ttcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditThongTinCaNhan(Gravity.CENTER);
            }
        });

        //button edit mật khẩu
        txt_DoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditMatKhau(Gravity.CENTER);
            }
        });

    }

    private void openEditMatKhau(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thaydoi_matkhau);

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

        //anh xa
        Button btnCapNhat = dialog.findViewById(R.id.btnCapNhat);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        EditText mk1 = dialog.findViewById(R.id.edt_thay_doi_mk1);
        EditText mk2 = dialog.findViewById(R.id.edt_thay_doi_mk2);
        EditText mk3 = dialog.findViewById(R.id.edt_thay_doi_mk3);



        DatabaseReference myRefBN = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BENHNHAN);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mk1.getText().toString())
                        || TextUtils.isEmpty(mk2.getText().toString())
                        || TextUtils.isEmpty(mk3.getText().toString()))
                {
                    Toast.makeText(TrangCaNhan.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (mk2.getText().toString().equals(mk3.getText().toString()))
                    {

                        DatabaseReference myRefBN = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BENHNHAN);
                        myRefBN.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    NguoiDung nguoiDung = dataSnapshot.getValue(NguoiDung.class);
                                        DataLocalManager.setIDNguoiDung(dataSnapshot.getKey());
                                        if (mk1.getText().toString().trim().equals(nguoiDung.getMatKhau()))
                                        {
                                            String mk_2 = mk2.getText().toString();
                                            HashMap hashMap1 = new HashMap();
                                            hashMap1.put("matKhau", mk_2);

                                            myRefBN.child(idNguoiDung).updateChildren(hashMap1).addOnSuccessListener(new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(Object o) {
                                                    Toast.makeText(TrangCaNhan.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                    FirebaseAuth.getInstance().signOut();
                                                    startActivity(new Intent(TrangCaNhan.this, LoiChao.class));
                                                }
                                            });
                                            break;
                                        }
//                                        else
//                                        {
//                                            Toast.makeText(TrangCaNhan.this,"Mật khẩu cũ không đúng!",Toast.LENGTH_LONG).show();
//                                        }

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(TrangCaNhan.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(TrangCaNhan.this, "Nhập lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void openEditThongTinCaNhan(int gravity) {
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

        //anh xa
        Button btnCapNhat = dialog.findViewById(R.id.btnCapNhat);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        EditText ten = dialog.findViewById(R.id.edt_update_ten);
        EditText quan_huyen = dialog.findViewById(R.id.edt_update_dia_chi_quan);
        EditText tinh_tp = dialog.findViewById(R.id.edt_update_dia_chi_tp);
        EditText mail = dialog.findViewById(R.id.edt_update_mail);
        //        EditText sdt = dialog.findViewById(R.id.edt_update_sdt);
        DatePicker ngaysinh = dialog.findViewById(R.id.update_ngaysinh);


            //hien thi thong tin
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
                            ten.setText(nguoiDung.getTenNguoiDung());
                            quan_huyen.setText(nguoiDung.getQuan());
                            tinh_tp.setText(nguoiDung.getThanhpho());
                            mail.setText(nguoiDung.getEmail_sdt());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TrangCaNhan.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
                }
            });
            // cap nhat thong tin
            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(ten.getText().toString())
                            || TextUtils.isEmpty(quan_huyen.getText().toString())
                            || TextUtils.isEmpty(tinh_tp.getText().toString())
                            || TextUtils.isEmpty(mail.getText().toString())
                    ) {
                        Toast.makeText(TrangCaNhan.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                    } else {
                        String name = ten.getText().toString();
                        String district = quan_huyen.getText().toString();
                        String city = tinh_tp.getText().toString();
                        String email = mail.getText().toString();
                        HashMap hashMap = new HashMap();
                        hashMap.put("tenNguoiDung", name);
                        hashMap.put("email_sdt", email);
                        hashMap.put("quan", district);
                        hashMap.put("thanhpho", city);

                        myRefBN.child(idNguoiDung).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(TrangCaNhan.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                    }
                }
            });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
