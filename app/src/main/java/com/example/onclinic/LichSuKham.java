package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.LichSuAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.LichKham;
import com.example.model.LichSu;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LichSuKham extends AppCompatActivity {

    RecyclerView rcvLichSu;
    LichSuAdapter lichSuAdapter;
    List<LichSu> listLichSu;

    String idNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_kham);
        idNguoiDung = DataLocalManager.getIDNguoiDung();
        layDanhSachLichSuKham();
        addControls();
        addEvents();
    }

    private void layDanhSachLichSuKham() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference(NoteFireBase.LICHSU);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    LichSu lichSu = data.getValue(LichSu.class);
                    if(idNguoiDung.equals(lichSu.getLichKham().getIdBenhNhan()) || idNguoiDung.equals(lichSu.getPhongKham().getIdBacSi()))
                    {
                        listLichSu.add(lichSu);
                        Collections.sort(listLichSu,LichSu.LichSuDateDescendingComparator);
                    }
                }
                lichSuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LichSuKham.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addEvents() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(lichSuAdapter!=null)
            lichSuAdapter.release();
    }

    private void addControls() {
        rcvLichSu = findViewById(R.id.rcvLichSu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LichSuKham.this);
        rcvLichSu.setLayoutManager(layoutManager);

        listLichSu = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(listLichSu,LichSuKham.this);

        rcvLichSu.setAdapter(lichSuAdapter);
    }
}