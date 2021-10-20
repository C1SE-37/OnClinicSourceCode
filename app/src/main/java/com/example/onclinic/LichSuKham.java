package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.adapter.LichSuAdapter;
import com.example.model.LichSu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LichSuKham extends AppCompatActivity {

    ListView lvLichSu;
    ArrayList<LichSu> arrLichSu;
    LichSuAdapter lichSuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_kham);
        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        lvLichSu = findViewById(R.id.lvLichSu);
        arrLichSu = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(
                LichSuKham.this,
                R.layout.item_lich_su,
                arrLichSu
        );
        lvLichSu.setAdapter(lichSuAdapter);
    }
}