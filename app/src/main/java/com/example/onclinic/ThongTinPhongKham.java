package com.example.onclinic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThongTinPhongKham extends AppCompatActivity {

    TextView txtTenPhongKham, txtTenBS, txtChuyenKhoa, txtDiaChi, txtMoTa;
    TextView txtEdit;
    Button bntDanhGia;
    ImageView avatar;
    String idPhongKham;
    PhongKham phongKham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_phong_kham);

        AnhXa();
        getDataClinic();

    }

    private void getDataClinic() {
        idPhongKham = DataLocalManager.getIDPhongKham();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        DatabaseReference databaseReference1 = databaseReference.child(idPhongKham).child(NoteFireBase.PHONGKHAM);

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                phongKham = snapshot.getValue(PhongKham.class);

                if(phongKham != null){
                    String tenPK = phongKham.getTenPhongKham();
                    String chuyenKhoa = phongKham.getChuyenKhoa();
                    String diaChi = phongKham.getDiaChi();
                    String moTa = phongKham.getMoTa();
                    txtTenPhongKham.setText(tenPK);
                    txtChuyenKhoa.setText("Chuyên khoa: "+ chuyenKhoa);
                    txtDiaChi.setText("Địa chỉ: "+diaChi);
                    txtMoTa.setText("Mô tả"+moTa);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThongTinPhongKham.this, "Lỗi đọc dữ liệu", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void AnhXa(){
        txtTenBS = findViewById(R.id.txtTenBS);

        txtTenPhongKham = findViewById(R.id.txt_TenPhongKham);

        txtChuyenKhoa = findViewById(R.id.txtChuyenKhoa);
        txtDiaChi = findViewById(R.id.txtPhongkham_DiaChi);
        txtMoTa = findViewById(R.id.txtPhongkham_MoTa);

        avatar = findViewById(R.id.Phongkham_Avatar);

        txtEdit = findViewById(R.id.txt_Doi_ttpk);
        bntDanhGia = findViewById(R.id.btn_DanhGia);
    }
}
