package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.local_data.DataLocalManager;
import com.example.model.DanhGia;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class VietDanhGia extends AppCompatActivity {
    EditText edtBinhLuan;
    Button bntSubmitDanhGia;
    RatingBar rating_bar;
    NguoiDung nguoiDung;
    String idPhongKham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        idPhongKham = (String) bundle.getSerializable("OBJECT_DANH_GIA1");
        AnhXa();
        nguoiDung = DataLocalManager.getNguoiDung();
        bntSubmitDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyTaoDanhGia();
            }
        });
    }

    private void xulyTaoDanhGia(){
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference();
        String keyDanhGia = myRef.child(NoteFireBase.PHONGKHAM).child(idPhongKham).child(NoteFireBase.DANHGIA).push().getKey();//lưu key để chỉnh sửa lịch khám về sau
        Float rating = rating_bar.getRating();
        String nhanxet = edtBinhLuan.getText().toString().trim();
        String idNguoiDung = nguoiDung.getUserID();
        DanhGia danhGia = new DanhGia(rating, nhanxet, idNguoiDung);
        danhGia.setIdDanhGia(keyDanhGia);
        myRef.child(NoteFireBase.PHONGKHAM).child(idPhongKham).child(NoteFireBase.DANHGIA).child(keyDanhGia).setValue(danhGia);
        Toast.makeText(VietDanhGia.this, "Cảm ơn bạn đã phản hồi", Toast.LENGTH_SHORT).show();

        XulyQuayLaiViewDanhGia(idPhongKham);
    }

    private void XulyQuayLaiViewDanhGia(String idPhongKham)
    {
        Intent intent = new Intent(VietDanhGia.this, ViewDanhGia.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT_DANH_GIA", idPhongKham);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void AnhXa(){
        rating_bar = findViewById(R.id.rating_bar);
        edtBinhLuan = findViewById(R.id.edt_BinhLuan);
        bntSubmitDanhGia = findViewById(R.id.btnSubmitDanhGia);
    }
}