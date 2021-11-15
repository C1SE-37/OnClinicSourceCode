package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.model.DonThuoc;
import com.example.model.NguoiDung;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NhapDonThuoc extends AppCompatActivity {

    EditText edt_TenThuoc, edt_SoLuongThuoc,edt_DonViTinh,edt_LieuDung;
    Button btnGuiDonThuoc, btnThemThuoc;
    ProgressDialog dialogProcess;
    ListView lv_DonThuoc;
    DonThuoc donThuoc;
    NguoiDung nguoiDung;
    String idNguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_don_thuoc);

        addCtrls();
        addEvents();
    }

    private void addEvents() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BENHNHAN);
        idNguoiDung = nguoiDung.getUserID();


    }

    private void addCtrls() {
        edt_TenThuoc = findViewById(R.id.edt_TenThuoc);
        edt_SoLuongThuoc = findViewById(R.id.edt_SoLuongThuoc);
        edt_DonViTinh = findViewById(R.id.edt_DonViTinh);
        edt_LieuDung = findViewById(R.id.edt_LieuDung);
        btnGuiDonThuoc = findViewById(R.id.btnGuiDonThuoc);
        btnThemThuoc = findViewById(R.id.btnThemThuoc);
        lv_DonThuoc = findViewById(R.id.lv_DonThuoc);

        dialogProcess = new ProgressDialog(this);

    }
}