package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.local_data.DataLocalManager;
import com.example.model.DanhGia;
import com.example.model.PhongKham;
import com.example.helper.CheckData;
import com.example.helper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VietDanhGia extends AppCompatActivity {
    EditText edtBinhLuan;
    Button btnSubmitDanhGia;
    RatingBar rating_bar;
    String idDguoiDung;
    PhongKham phongKham;
    DanhGia DG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        phongKham = (PhongKham) bundle.getSerializable("OBJECT_PHONG_KHAM4");
        DG = (DanhGia) bundle.getSerializable("OBJECT_DANH_GIA");
        idDguoiDung = DataLocalManager.getIDNguoiDung();
        AnhXa();
        btnSubmitDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyHoanThanhDanhGia();
            }
        });
    }

    private void layDanhSachDanhGiaTuFireBase(IVietDanhGia iVietDanhGia) {
        DatabaseReference refTBDanhGia = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference()
                .child(NoteFireBase.PHONGKHAM).child(phongKham.getIdPhongKham()).child(NoteFireBase.DANHGIA);
        refTBDanhGia.addValueEventListener(new ValueEventListener() {
            float tongDanhGia = 0;
            int listSize = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren())
                {
                    DanhGia danhGia = data.getValue(DanhGia.class);
                    if(DG!=null)
                    {
                        if(!DG.getIdNguoiDungDG().equals(danhGia.getIdNguoiDungDG()))
                        {
                            tongDanhGia +=  danhGia.getRating();
                            listSize++;
                        }
                    }
                    else{
                        tongDanhGia +=  danhGia.getRating();
                        listSize++;
                    }
                }
                iVietDanhGia.layTongDanhGiaVaKichThuocDanhSach(tongDanhGia, listSize);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface IVietDanhGia{
        void layTongDanhGiaVaKichThuocDanhSach(float danhGia, int kichThuoc);
    }

    private void xulyHoanThanhDanhGia(){
        if(checkInput()) {
            DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference();
            Float rating = rating_bar.getRating();
            String nhanxet = edtBinhLuan.getText().toString().trim();
            //phongKham.setTbDanhGia((Float) (rating + tongDanhGia) / (kichThuocDanhSach + 1));
            //myRef.child(NoteFireBase.PHONGKHAM).child(phongKham.getIdPhongKham()).setValue(phongKham);
            DanhGia danhGia = new DanhGia(idDguoiDung, rating, nhanxet);
            myRef.child(NoteFireBase.PHONGKHAM).child(phongKham.getIdPhongKham()).child(NoteFireBase.DANHGIA)
                    .child(idDguoiDung).setValue(danhGia, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(VietDanhGia.this, "Cảm ơn bạn đã phản hồi", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }

    private boolean checkInput()
    {
        if(CheckData.isEmpty(edtBinhLuan) && rating_bar.getRating()!=0)
            return true;
        else return false;
    }

    private void AnhXa(){
        rating_bar = findViewById(R.id.rating_bar);
        edtBinhLuan = findViewById(R.id.edt_BinhLuan);
        btnSubmitDanhGia = findViewById(R.id.btnSubmitDanhGia);
        if(DG!=null)
        {
            rating_bar.setRating(DG.getRating());
            edtBinhLuan.setText(DG.getNhanXet());
        }
    }
}