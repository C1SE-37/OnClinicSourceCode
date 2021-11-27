package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VietDanhGia extends AppCompatActivity {
    EditText edtBinhLuan;
    Button bntSubmitDanhGia;
    RatingBar rating_bar;
    NguoiDung nguoiDung;
    PhongKham phongKham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        phongKham = (PhongKham) bundle.getSerializable("OBJECT_PHONG_KHAM4");
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
        Float rating = rating_bar.getRating();
        String nhanxet = edtBinhLuan.getText().toString().trim();
        String idNguoiDung = nguoiDung.getUserID();
        DanhGia danhGia = new DanhGia(idNguoiDung,rating, nhanxet);
        myRef.child(NoteFireBase.PHONGKHAM).child(phongKham.getIdPhongKham()).child(NoteFireBase.DANHGIA).child(idNguoiDung).setValue(danhGia, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(VietDanhGia.this, "Cảm ơn bạn đã phản hồi", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        List<DanhGia> danhGiaList = new ArrayList<>();
        DatabaseReference refTBDanhGia = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference()
                .child(NoteFireBase.PHONGKHAM).child(phongKham.getIdPhongKham()).child(NoteFireBase.DANHGIA);
        refTBDanhGia.addValueEventListener(new ValueEventListener() {
            float tongDanhGia = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                danhGiaList.clear();
                for(DataSnapshot data: snapshot.getChildren())
                {
                    DanhGia danhGia = data.getValue(DanhGia.class);
                    tongDanhGia +=  danhGia.getRating();
                    danhGiaList.add(danhGia);
                }
                phongKham.setTbDanhGia(tongDanhGia/danhGiaList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void AnhXa(){
        rating_bar = findViewById(R.id.rating_bar);
        edtBinhLuan = findViewById(R.id.edt_BinhLuan);
        bntSubmitDanhGia = findViewById(R.id.btnSubmitDanhGia);
    }
}