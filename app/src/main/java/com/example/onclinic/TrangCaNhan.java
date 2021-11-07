package com.example.onclinic;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;

public class TrangCaNhan extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;

    private Button btn_DangXuat;
    private ImageButton edit_birth, edit_mail, edit_address;
    private TextView txt_DoiTac, txt_DoiMK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);
        userid = DataLocalManager.getIDNguoiDung();

        getData();
        btnDangxuat();
        btnS();
    }

    //do du lieu len profile
    private void getData() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG);
        DatabaseReference refNguoiDung = myRef.child(userid).child(NoteFireBase.NGUOIDUNG);

        final TextView hovaTen = (TextView) findViewById(R.id.HovaTen);
        final TextView txtProfile_ngaysinh = (TextView) findViewById(R.id.txtProfile_ngaysinh);
        final TextView txtProfile_phone = (TextView) findViewById(R.id.txtProfile_phone);
        final TextView txtProfile_Mail = (TextView) findViewById(R.id.txtProfile_Mail);
        final TextView txtProfile_address = (TextView) findViewById(R.id.txtProfile_address);

        refNguoiDung.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NguoiDung user = snapshot.getValue(NguoiDung.class);

                if (user != null) {
                    String tenNguoiDung = user.tenNguoiDung;
                    String ngaysinh = user.ngaySinh;
                    String mail = user.email_sdt;
                    String address1 = user.quan;
                    String address2 = user.thanhpho;

                    hovaTen.setText(tenNguoiDung);
                    txtProfile_ngaysinh.setText(ngaysinh);
                    txtProfile_Mail.setText(mail);
                    txtProfile_address.setText(address1 + ", " + address2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrangCaNhan.this, "Lỗi đọc dữ liệu", Toast.LENGTH_LONG).show();
            }
        });

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

    private void btnS() {

        edit_birth = (ImageButton) findViewById(R.id.btn_edit1);
        edit_mail = (ImageButton) findViewById(R.id.btn_edit3);
        edit_address = (ImageButton) findViewById(R.id.btn_edit4);
        txt_DoiTac = (TextView) findViewById(R.id.txt_DoiTac);
        txt_DoiMK = (TextView) findViewById(R.id.txt_DoiMK);

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

        //button doi mk
        txt_DoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDoiMK(Gravity.CENTER);
            }
        });

        //button doi tac
        txt_DoiTac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangCaNhan.this, TaoPhongKham.class));
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
    }

    private void openEditDiaChi(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thay_doi_dia_chi);

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

    private void openDoiMK(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thay_doi_mk);

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
