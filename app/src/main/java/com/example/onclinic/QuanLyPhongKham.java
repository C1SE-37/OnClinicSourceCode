package com.example.onclinic;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.LichKham;
import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLyPhongKham extends AppCompatActivity {

    ImageView imgPhongKham, imgNgay, imgGio;
    TextView txtTenPhongKham, txtChuyenKhoa, txtNgay, txtGio;
    Button btnTaoSuatKham, btnSuatKhamDaTao, btnLichKhamSapToi;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

    ArrayList<PhongKham> phongKhamList = new ArrayList<>();
    String idNguoiDung;
    NguoiDung nguoiDung;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phong_kham);
        idNguoiDung = DataLocalManager.getIDNguoiDung();;
        nguoiDung = DataLocalManager.getNguoiDung();
        docDuLieuFireBase();
        addControls();
        addEvents();
    }

    private void docDuLieuFireBase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    PhongKham phong = dataSnapshot.getValue(PhongKham.class);
                    DataLocalManager.setIDPhongKham(dataSnapshot.getKey());//lưu id vào data local để sử dụng về sau
                    if(idNguoiDung.equals(phong.getIdBacSi().toString().trim()))
                    {
                        txtTenPhongKham.setText(phong.getTenPhongKham());
                        txtChuyenKhoa.setText(phong.getChuyenKhoa());
                        if(phong.getHinhAnh()!=null) {
                            byte[] decodedString = Base64.decode(phong.getHinhAnh(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            imgPhongKham.setImageBitmap(decodedByte);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addEvents() {
        imgNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiCalendar();
            }
        });
        imgGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hienThiClock();
            }
        });
        btnTaoSuatKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTaoSuatKham();
            }
        });
    }

    private void xuLyTaoSuatKham() {
        try {
            DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference();
            String idPhongKham = DataLocalManager.getIDPhongKham();
            String ngay = txtNgay.getText().toString().trim();
            String gio = txtGio.getText().toString().trim();
            LichKham lichKham = new LichKham(ngay,gio);
            myRef.child(NoteFireBase.PHONGKHAM).child(idPhongKham).child(NoteFireBase.LICHKHAM).child(ngay).setValue(lichKham);
            Toast.makeText(QuanLyPhongKham.this,"Tạo 1 lịch khám thành công",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(QuanLyPhongKham.this, "Lỗi tạo lịch khám", Toast.LENGTH_LONG).show();
        }
    }

    private void hienThiClock() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                txtGio.setText(sdf2.format(calendar.getTime()));
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(QuanLyPhongKham.this,
                android.R.style.Theme_Holo_Light_Dialog,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void hienThiCalendar() {
        //bắt sự kiện khi click trên calendar
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR,year);
                calendar.set(calendar.MONTH,month);
                calendar.set(calendar.DAY_OF_MONTH,dayOfMonth);
                txtNgay.setText(sdf1.format(calendar.getTime()));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(QuanLyPhongKham.this,
                android.R.style.Theme_Holo_Light_Dialog,
                dateSetListener,
                calendar.get(calendar.YEAR),
                calendar.get(calendar.MONTH),
                calendar.get(calendar.DAY_OF_MONTH));
        //làm mờ màn hình chính sau khi hiện calendar
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void addControls() {
        imgPhongKham = findViewById(R.id.imgPhongKham);
        imgNgay = findViewById(R.id.imgDate);
        imgGio = findViewById(R.id.imgClock);
        txtTenPhongKham = findViewById(R.id.txtTenPhongKham);
        txtChuyenKhoa = findViewById(R.id.txtChuyenKhoa);
        txtNgay = findViewById(R.id.edtChonNgay);
        txtNgay.setText(sdf1.format(calendar.getTime()));
        txtGio = findViewById(R.id.edtChonGio);
        txtGio.setText(sdf2.format(calendar.getTime()));
        btnTaoSuatKham = findViewById(R.id.btnTaoSuatKham);
        btnSuatKhamDaTao = findViewById(R.id.btnSuatKhamDaTao);
        btnLichKhamSapToi = findViewById(R.id.btnLichKhamSapToi);
    }
}
