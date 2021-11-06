package com.example.onclinic;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.NguoiDung;
import com.example.onclinic.dialogs.editDiaChi;
import com.example.onclinic.dialogs.editMail;
import com.example.onclinic.dialogs.editNgaySinh;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrangCaNhan extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button btn_DangXuat;
    private ImageButton edit_birth, edit_mail, edit_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        //button dang xuat
        btn_DangXuat = (Button) findViewById(R.id.btn_DangXuat);

        btn_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TrangCaNhan.this, LoiChao.class));
            }
        });

        //button edit ngaysinh
        edit_birth = (ImageButton) findViewById(R.id.btn_edit1);
        edit_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //button edit email
        edit_mail = (ImageButton) findViewById(R.id.btn_edit3);
        edit_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //button edit dia chi
        edit_address = (ImageButton) findViewById(R.id.btn_edit4);
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //do su lieu len profile
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("NGUOIDUNG");
        userID = user.getUid();

        final  TextView HovaTen = (TextView) findViewById(R.id.HovaTen);
        final  TextView txtProfile_ngaysinh = (TextView) findViewById(R.id.txtProfile_ngaysinh);
        final  TextView txtProfile_phone = (TextView) findViewById(R.id.txtProfile_phone);
        final  TextView txtProfile_Mail = (TextView) findViewById(R.id.txtProfile_Mail);
        final  TextView txtProfile_address = (TextView) findViewById(R.id.txtProfile_address);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NguoiDung user = snapshot.getValue(NguoiDung.class);

                if (user != null){
                    String tenNguoiDung = user.tenNguoiDung;
                    String ngaysinh = user.ngaySinh;
                    String mail = user.email_sdt;
                    String address = user.thanhpho;

                    HovaTen.setText(tenNguoiDung);
                    txtProfile_ngaysinh.setText(ngaysinh);
                    txtProfile_Mail.setText(mail);
                    txtProfile_address.setText(address);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TrangCaNhan.this, "Lỗi", Toast.LENGTH_LONG).show();
            }
        });

    }
}
