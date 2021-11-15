package com.example.onclinic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThongTinPhongKham extends AppCompatActivity {

    TextView txtTenPhongKham, txtTenBS, txtChuyenKhoa, txtMail, txtDiaChi, txtMoTa;
    TextView txtEdit;
    Button bntDanhGia;
    ImageView avatar;
    String idPhongKham;
    NguoiDung nguoiDung;
    PhongKham phongKham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_phong_kham);

        phongKham = DataLocalManager.getPhongKham();

        txtTenPhongKham = findViewById(R.id.txt_TenPhongKham);
        txtTenPhongKham.setText(phongKham.getTenPhongKham());

//        nguoiDung = DataLocalManager.getNguoiDung();
//        AnhXa();
    }

    private void AnhXa(){
        txtTenBS = findViewById(R.id.txtTenBS);
        txtTenBS.setText("Bác sĩ: "+nguoiDung.getTenNguoiDung());
        txtMail = findViewById(R.id.txtPhongKham_mail2);
        txtMail.setText("Thư điện tử: "+nguoiDung.getEmail_sdt());
        txtTenPhongKham = findViewById(R.id.txt_TenPhongKham);
        txtTenPhongKham.setText(phongKham.getTenPhongKham());
        txtChuyenKhoa = findViewById(R.id.txtChuyenKhoa);
        txtChuyenKhoa.setText("Chuyên khoa: "+phongKham.getChuyenKhoa());
        txtDiaChi = findViewById(R.id.txtPhongkham_DiaChi);
        txtDiaChi.setText("Địa chỉ: "+phongKham.getDiaChi());
        txtMoTa = findViewById(R.id.txtPhongkham_MoTa);
        txtMoTa.setText("Mô tả: "+phongKham.getMoTa());
        avatar = findViewById(R.id.Phongkham_Avatar);
        if(phongKham.getHinhAnh()!=null) {
            byte[] decodedString = Base64.decode(phongKham.getHinhAnh(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            avatar.setImageBitmap(decodedByte);
        }
        txtEdit = findViewById(R.id.txt_Doi_ttpk);
        bntDanhGia = findViewById(R.id.btn_DanhGia);
    }
}
