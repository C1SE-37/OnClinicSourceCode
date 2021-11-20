package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.DanhGiaAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.DanhGia;
import com.example.model.LichKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewDanhGia extends AppCompatActivity {

    Button VietDanhGia;
    RecyclerView rcvDanhGia;
    DanhGiaAdapter mDanhGiaAdapter;
    List<DanhGia> mListDanhGia;
    String idPhongKham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phan_hoi);
        anhXa();
        addEvent();
        idPhongKham = DataLocalManager.getIDPhongKham();
        getListDanhGiaFromRealtimeDatabase();
    }

    private void addEvent(){
        VietDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDanhGia.this, com.example.onclinic.VietDanhGia.class);
                startActivity(intent);
            }
        });
    }

    private void getListDanhGiaFromRealtimeDatabase(){
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        DatabaseReference refSuatKham = myRef.child(idPhongKham).child(NoteFireBase.DANHGIA);
        refSuatKham.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListDanhGia.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    DanhGia danhGia = data.getValue(DanhGia.class);
                    mListDanhGia.add(danhGia);
                }
                mDanhGiaAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewDanhGia.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void anhXa(){
        VietDanhGia = findViewById(R.id.btn_VietDanhGia);
        rcvDanhGia = findViewById(R.id.rcv_DanhGia);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDanhGia.setLayoutManager(linearLayoutManager);

        mListDanhGia = new ArrayList<>();
        mDanhGiaAdapter = new DanhGiaAdapter(mListDanhGia);

        rcvDanhGia.setAdapter(mDanhGiaAdapter);
    }
}