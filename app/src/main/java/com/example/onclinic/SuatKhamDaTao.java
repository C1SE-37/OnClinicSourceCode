package com.example.onclinic;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.SuatKhamAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.LichKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SuatKhamDaTao extends AppCompatActivity {

    private RecyclerView rcvLichKham;
    private SuatKhamAdapter suatKhamAdapter;
    private List<LichKham> listLichKham;

    String idPhongKham;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suat_kham_da_tao);
        idPhongKham = DataLocalManager.getIDPhongKham();
        layDanhSachLichKhamTuFireBase();
        addControls();
    }

    private void layDanhSachLichKhamTuFireBase()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        DatabaseReference refSuatKham = myRef.child(idPhongKham).child(NoteFireBase.LICHKHAM);
        refSuatKham.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLichKham.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    LichKham lichKham = data.getValue(LichKham.class);
                    listLichKham.add(lichKham);
                }
                suatKhamAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SuatKhamDaTao.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(suatKhamAdapter!= null)
            suatKhamAdapter.release();
    }

    private void addControls() {
        rcvLichKham = findViewById(R.id.lvSuatKham);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SuatKhamDaTao.this);
        rcvLichKham.setLayoutManager(layoutManager);

        listLichKham = new ArrayList<>();
        suatKhamAdapter = new SuatKhamAdapter(listLichKham, SuatKhamDaTao.this);

        rcvLichKham.setAdapter(suatKhamAdapter);
    }

}
