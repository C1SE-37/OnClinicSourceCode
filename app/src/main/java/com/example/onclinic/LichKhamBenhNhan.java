package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.adapter.LichKhamBenhNhanAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.LichKham;
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LichKhamBenhNhan extends AppCompatActivity {

    private RecyclerView rcvLichKham;
    private LichKhamBenhNhanAdapter lichKhamBenhNhanAdapter;
    private List<LichKham> listLichKham = new ArrayList<>();
    private List<PhongKham> listPhongKham = new ArrayList<>();//toàn bộ phòng khám trên firebase
    private List<PhongKham> listPhongKhamCoLichKham = new ArrayList<>();//tìm được phòng khám có lịch khám bệnh nhân này thì thêm vào
    String idNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_kham_benh_nhan);
        idNguoiDung = DataLocalManager.getIDNguoiDung();
        layDanhSachLichKhamTuFireBase();
        addControls();
    }

    private void layDanhSachLichKhamTuFireBase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference(NoteFireBase.PHONGKHAM);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listPhongKham.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    PhongKham phongKham = data.getValue(PhongKham.class);
                    listPhongKham.add(phongKham);
                }
                for(PhongKham phong: listPhongKham)
                {
                    String key = phong.getIdPhongKham();
                    DatabaseReference refLichKham = myRef.child(key).child(NoteFireBase.LICHKHAM);
                    refLichKham.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data: snapshot.getChildren())
                            {
                                LichKham lich = data.getValue(LichKham.class);
                                if(idNguoiDung.equals(lich.getIdBenhNhan()) && lich.getTrangThai() <=LichKham.DatLich)
                                {
                                    listPhongKhamCoLichKham.add(phong);
                                    listLichKham.add(lich);
                                    Collections.sort(listLichKham, LichKham.LichKhamDateAsendingComparator);//sắp xếp ngày tăng dần
                                }
                            }
                            lichKhamBenhNhanAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                lichKhamBenhNhanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LichKhamBenhNhan.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(lichKhamBenhNhanAdapter != null)
            lichKhamBenhNhanAdapter.release();
    }

    private void xuLyThamGia(LichKham lichKham, PhongKham phongKham) {
        Intent intent = new Intent(LichKhamBenhNhan.this,KhamOnlineDemo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("LICH_KHAM",lichKham);
        bundle.putSerializable("PHONG_KHAM",phongKham);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void addControls() {
        rcvLichKham = findViewById(R.id.rcvLichKhamBN);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LichKhamBenhNhan.this);
        rcvLichKham.setLayoutManager(layoutManager);

        listLichKham = new ArrayList<>();
        lichKhamBenhNhanAdapter = new LichKhamBenhNhanAdapter(listLichKham, LichKhamBenhNhan.this, new LichKhamBenhNhanAdapter.ILichKhamBenhNhanAdapter() {
            @Override
            public void clickThamGia(LichKham lichKham, PhongKham phongKham) {
                xuLyThamGia(lichKham, phongKham);
            }

            @Override
            public List<PhongKham> layDanhSachPhongKham() {
                return listPhongKhamCoLichKham;
            }

        });

        rcvLichKham.setAdapter(lichKhamBenhNhanAdapter);
    }
}