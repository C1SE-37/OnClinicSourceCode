package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.GioKhamAdapter;
import com.example.local_data.DataLocalManager;
import com.example.model.LichKham;
import com.example.model.PhongKham;
import com.example.sqlhelper.CheckData;
import com.example.sqlhelper.NgayGio;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DatPhong2 extends AppCompatActivity {
    ImageView imgPhongKham;
    TextView txtPhongKham, txtChuyenKhoa;
    RatingBar ratingBar;
    RadioButton rdoOnline,rdoOffline;
    ConstraintLayout linearThongTinPhongKham;

    Spinner spnNgayKham;
    ArrayList<String> dsNgayKham;
    ArrayAdapter<String> adapterNgayKham;

    GridView gvGioKham;
    ArrayList<Date> dsGioKham;
    GioKhamAdapter adapterGioKham;

    Button btnDatPhong,btnHuy;

    PhongKham phongKham;
    LichKham lichKham;

    String idNguoiDung,idLichKham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_phong2);
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) return;
        phongKham = (PhongKham) bundle.getSerializable("OBJECT_PHONG_KHAM");
        idNguoiDung = DataLocalManager.getIDNguoiDung();
        
        addControls();
        docDuLieuFirebaseVaoSpinner();
        addEvents();
    }

    private void docDuLieuFirebaseVaoSpinner() {
        try {
            DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
            DatabaseReference refLichKham = myRef.child(phongKham.getIdPhongKham()).child(NoteFireBase.LICHKHAM);
            refLichKham.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    dsNgayKham.clear();
                    for(DataSnapshot data : snapshot.getChildren())
                    {
                        LichKham lichKham = data.getValue(LichKham.class);
                        try {
                            Date ngayFirebase = NgayGio.ConvertStringToDate(lichKham.getNgayKham());
                            Date ngayHienTai = NgayGio.GetDateCurrent();
                            //nếu ds chưa chứa ngày đó, id bệnh nhân chưa có và ngày trong lịch đặt phải lớn hơn hoặc = hiện tại thì thêm vào
                            if(!dsNgayKham.contains(lichKham.getNgayKham()) && lichKham.getIdBenhNhan()==null && ngayFirebase.getTime()>=ngayHienTai.getTime())
                            {
                                dsNgayKham.add(lichKham.getNgayKham());
                                Collections.sort(dsNgayKham, LichKham.StringDateAsendingComparator);//sắp xếp ngày tăng dần
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    adapterNgayKham = new ArrayAdapter<String>(DatPhong2.this, android.R.layout.simple_spinner_item,dsNgayKham);
                    spnNgayKham.setAdapter(adapterNgayKham);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(DatPhong2.this, "Lỗi đọc dữ liệu", Toast.LENGTH_LONG).show();
        }
    }

    private void addEvents() {
        linearThongTinPhongKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatPhong2.this, ThongTinPhongKhamViewBenhNhan.class);
                startActivity(intent);
            }
        });

        spnNgayKham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ngaySpinner = dsNgayKham.get(position);//chọn ở spinner
                try {
                    DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
                    DatabaseReference refLichKham = myRef.child(phongKham.getIdPhongKham()).child(NoteFireBase.LICHKHAM);

                    refLichKham.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            dsGioKham.clear();
                            for (DataSnapshot data : snapshot.getChildren())
                            {
                                try{
                                    Date ngayDangChon = NgayGio.ConvertStringToDate(ngaySpinner);
                                    LichKham lichKham = data.getValue(LichKham.class);
                                    Date dateFirebase = NgayGio.ConvertStringToDate(lichKham.getNgayKham());
                                    Date ngayHienTai = NgayGio.GetDateCurrent();
                                    if (ngayDangChon.equals(dateFirebase)) {
                                        if(ngayDangChon.equals(ngayHienTai))
                                        {
                                            try{
                                                Date gioFirebase = NgayGio.ConvertStringToTime(lichKham.getGioKham());
                                                Date gioHienTai = NgayGio.GetTimeCurrent();
                                                if(gioFirebase.getTime()>=gioHienTai.getTime()) {
                                                    dsGioKham.add(gioFirebase);
                                                    Collections.sort(dsGioKham, LichKham.TimeAsendingComparator);//sắp xếp giờ tăng dần
                                                }
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        else{
                                            try{
                                                Date hour = NgayGio.ConvertStringToTime(lichKham.getGioKham());
                                                dsGioKham.add(hour);
                                                Collections.sort(dsGioKham, LichKham.TimeAsendingComparator);//sắp xếp giờ tăng dần
                                            }
                                            catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapterGioKham = new GioKhamAdapter(DatPhong2.this, R.layout.item_gio_kham, dsGioKham);
                            gvGioKham.setAdapter(adapterGioKham);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(DatPhong2.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                catch (Exception ex)
                {
                    Toast.makeText(DatPhong2.this, "Lỗi đọc dữ liệu", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gvGioKham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
                DatabaseReference refLichKham = myRef.child(phongKham.getIdPhongKham()).child(NoteFireBase.LICHKHAM);
                refLichKham.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Object gio = gvGioKham.getItemAtPosition(position);
                        String gioHT = new SimpleDateFormat("HH:mm").format(gio);
                        for(DataSnapshot data : snapshot.getChildren())
                        {
                            LichKham lk= data.getValue(LichKham.class);
                            if(gioHT.equals(lk.getGioKham()) && spnNgayKham.getSelectedItem().equals(lk.getNgayKham()))
                            {
                                lichKham = lk;
                                idLichKham = data.getKey();
                                Toast.makeText(DatPhong2.this, " Ngày: "+lk.getNgayKham()+" vào lúc: "+gioHT, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DatPhong2.this, "Lỗi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnDatPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDatPhong();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(DatPhong2.this)
                        .setTitle("Thông báo").setMessage("Bạn muốn Hủy đặt lịch?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });
    }

    private void xuLyDatPhong() {
        if(checkInput()) {
            DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
            //gán hình thức vào lịch
            String hinhThuc = "";
            if (rdoOffline.isChecked()) hinhThuc += "Trực tiếp";
            else hinhThuc = "Online";
            lichKham.setHinhThucKham(hinhThuc);
            //gán id bệnh nhân vào lịch
            lichKham.setIdBenhNhan(idNguoiDung);
            lichKham.setTrangThai(LichKham.DatLich);
            myRef.child(phongKham.getIdPhongKham()).child(NoteFireBase.LICHKHAM).child(idLichKham).setValue(lichKham);
            Toast.makeText(DatPhong2.this, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
            troVeManHinhTrangChu();
        }
    }

    private void troVeManHinhTrangChu() {
        Intent intent = new Intent(DatPhong2.this,TrangChuBenhNhan.class);
        startActivity(intent);
        finishAffinity();
    }

    private boolean checkInput()
    {
        if(!rdoOffline.isChecked() && !rdoOnline.isChecked())
        {
            rdoOffline.setTextColor(Color.RED);
            rdoOnline.setTextColor(Color.RED);
            return false;
        }
        /*if(gvGioKham.getSelectedItem() == null)
        {
            Toast.makeText(DatPhong2.this,"Chưa chọn giờ khám",Toast.LENGTH_LONG).show();
            return false;
        }*/
        return true;
    }

    private void addControls() {
        imgPhongKham = findViewById(R.id.imgPhongKham2);
        if(phongKham.getHinhAnh()!=null) {
            byte[] decodedString = Base64.decode(phongKham.getHinhAnh(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgPhongKham.setImageBitmap(decodedByte);
        }
        txtPhongKham = findViewById(R.id.txtPhongKham2);
        txtPhongKham.setText("Phòng khám "+phongKham.getTenPhongKham());
        txtChuyenKhoa = findViewById(R.id.txtChuyenKhoa2);
        txtChuyenKhoa.setText("Chuyên khoa "+phongKham.getChuyenKhoa());
        ratingBar = findViewById(R.id.rating_bar2);
        linearThongTinPhongKham = findViewById(R.id.LayoutPhongKham);

        rdoOnline = findViewById(R.id.rdoOnline);
        rdoOffline = findViewById(R.id.rdoOffline);
        if (phongKham.getHinhThucKham().equals("Online"))
        {
            rdoOffline.setEnabled(false);
            rdoOnline.setChecked(true);
        }
        else if (phongKham.getHinhThucKham().equals("Trực tiếp")) {
            rdoOnline.setEnabled(false);
            rdoOffline.setChecked(true);
        }
        spnNgayKham = findViewById(R.id.spnNgayKham);
        dsNgayKham = new ArrayList<>();

        gvGioKham = findViewById(R.id.gvGioKham2);
        dsGioKham = new ArrayList<>();
        btnDatPhong = findViewById(R.id.btnDatPhong);
        btnHuy = findViewById(R.id.btnHuyDP2);
    }
}