package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.DanhGiaAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.DanhGia;
import com.example.model.LichKham;
import com.example.model.PhongKham;
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
    TextView tenPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phan_hoi);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        idPhongKham = (String) bundle.getSerializable("OBJECT_DANH_GIA");
        anhXa();
        addEvent();
        getListDanhGiaFromRealtimeDatabase();
    }

    private void addEvent(){
        VietDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XuLyVietDanhGia(idPhongKham);
            }
        });
    }

    private void getListDanhGiaFromRealtimeDatabase(){
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        DatabaseReference refDanhGia = myRef.child(idPhongKham).child(NoteFireBase.DANHGIA);
        refDanhGia.addValueEventListener(new ValueEventListener() {
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

    private void XuLyVietDanhGia(String idPhongKham)
    {
        Intent intent = new Intent(ViewDanhGia.this, VietDanhGia.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT_DANH_GIA1",idPhongKham);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void anhXa(){
        DatabaseReference myRefPK = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM).child(idPhongKham).child("tenPhongKham");
        tenPK = findViewById(R.id.txt_DanhGiaView_TenPK);
        myRefPK.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tenPK_ = snapshot.getValue(String.class);
                tenPK.setText("#Đánh giá của phòng khám " + tenPK_);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewDanhGia.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

        VietDanhGia = findViewById(R.id.btn_VietDanhGia);
        rcvDanhGia = findViewById(R.id.rcv_DanhGia);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDanhGia.setLayoutManager(linearLayoutManager);

        mListDanhGia = new ArrayList<>();
        mDanhGiaAdapter = new DanhGiaAdapter(mListDanhGia, ViewDanhGia.this);

        rcvDanhGia.setAdapter(mDanhGiaAdapter);
    }
}