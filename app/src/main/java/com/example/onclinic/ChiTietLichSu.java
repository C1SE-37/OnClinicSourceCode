package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.adapter.DonThuocAdapter;
import com.example.model.DonThuoc;
import com.example.model.LichSu;

import java.util.ArrayList;
import java.util.List;

public class ChiTietLichSu extends AppCompatActivity {

    TextView txtTenPhongKham, txtDiaChi, txtBacSi, txtEmail,txtNgayKham, txtHinhThuc, txtTroVe;
    TextView txtChanDoan, txtTrieuChung, txtChuY,txtTongTien;

    RecyclerView rcvDonThuoc;
    List<DonThuoc> listDonThuoc;
    DonThuocAdapter donThuocAdapter;

    LichSu lichSu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lich_su);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        lichSu = (LichSu) bundle.getSerializable("CHI_TIET_LICH_SU");
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addControls() {
        txtTenPhongKham = findViewById(R.id.txtTenPhongKhamCTLS);
        txtDiaChi = findViewById(R.id.txtDiaChiCTLS);
        txtBacSi = findViewById(R.id.txtBacSiCTLS);
        txtEmail = findViewById(R.id.txtEmailCTLS);
        txtNgayKham = findViewById(R.id.txtNgayKhamCTLS);
        txtHinhThuc = findViewById(R.id.txtHinhThucCTLS);
        txtTroVe = findViewById(R.id.txtTroVe);
        txtChanDoan = findViewById(R.id.txtChanDoanCTLS);
        txtTrieuChung = findViewById(R.id.txtTrieuChungCTLS);
        txtChuY = findViewById(R.id.txtChuYCTLS);
        txtTongTien = findViewById(R.id.txtTongTienCTLS);

        rcvDonThuoc = findViewById(R.id.rcvDonThuocCTLS);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChiTietLichSu.this);
        rcvDonThuoc.setLayoutManager(layoutManager);

        listDonThuoc = new ArrayList<>();
        donThuocAdapter = new DonThuocAdapter(listDonThuoc, ChiTietLichSu.this, new DonThuocAdapter.IDonThuocAdapter() {
            @Override
            public void clickItemDonThuoc(DonThuoc donThuoc) {

            }
        });
        rcvDonThuoc.setAdapter(donThuocAdapter);

    }
}