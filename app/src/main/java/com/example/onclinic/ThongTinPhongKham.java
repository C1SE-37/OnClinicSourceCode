package com.example.onclinic;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.local_data.DataLocalManager;
import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ThongTinPhongKham extends AppCompatActivity {

    TextView txtTenPhongKham, txtTenBS, txtEmail, txtChuyenKhoa, txtDiaChi, txtMoTa;
    TextView txtEdit;
    Button bntDanhGia;
    ImageView avatar;
    NguoiDung nguoiDung;
    PhongKham phongKham;

    //control của dialog
    ImageView anhPK;
    Button capnhat, huy;
    TextView chonAnh;
    EditText mail, diachi, mota;
    Bitmap selectedBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_phong_kham);
        phongKham = DataLocalManager.getPhongKham();
        nguoiDung = DataLocalManager.getNguoiDung();
        AnhXa();
        addEvents();
    }

    private void addEvents(){
        txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditThongTinPhongKham(Gravity.CENTER);
            }
        });

        bntDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTinPhongKham.this,ViewDanhGia.class);
                startActivity(intent);
            }
        });
    }

    private void openEditThongTinPhongKham(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thaydoi_thongtin_phongkham);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }

        //ánh xạ control dialog
        anhPK = dialog.findViewById(R.id.imgAnhPhongKham);
        capnhat = dialog.findViewById(R.id.btnCapNhatTTPK);
        huy = dialog.findViewById(R.id.btnHuyTTPK);
        chonAnh = dialog.findViewById(R.id.txtChonAnhPK);
        mail = dialog.findViewById(R.id.edt_update_mailPK);
        diachi = dialog.findViewById(R.id.edt_update_dia_chi_PK);
        mota = dialog.findViewById(R.id.edt_update_motaPK);

        mail.setText(nguoiDung.getEmail_sdt());
        diachi.setText(phongKham.getDiaChi());
        mota.setText(phongKham.getMoTa());

        //thêm ảnh
        chonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonAnh();
            }
        });

        //xử lý sự kiện nút update
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mail.getText().toString())
                        || TextUtils.isEmpty(diachi.getText().toString())
                        || TextUtils.isEmpty(mota.getText().toString()))
                {
                    Toast.makeText(ThongTinPhongKham.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mail_ = mail.getText().toString();
                    String diachi_ = diachi.getText().toString();
                    String mota_ = mota.getText().toString();

                    HashMap hashMap = new HashMap();
                    hashMap.put("diaChi", diachi_);
                    hashMap.put("moTa", mota_);

                    if(selectedBitmap!=null)
                    {
                        //đưa ảnh về kiểu chuỗi base64
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String imgEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        hashMap.put("hinhAnh",imgEncoded);
                    }

                    HashMap hashMap1 = new HashMap();
                    hashMap1.put("email_sdt", mail_);


                    DatabaseReference myRefPK = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
                    myRefPK.child(phongKham.getIdPhongKham()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(ThongTinPhongKham.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    });

                    DatabaseReference myRefBS = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BACSI);
                    myRefBS.child(nguoiDung.getUserID()).updateChildren(hashMap1).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                    });
                }
            }
        });

        //xử lý sự kiện nút hủy
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void xuLyChonAnh() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(ThongTinPhongKham.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                try {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(ThongTinPhongKham.this.getContentResolver(),imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anhPK.setImageBitmap(selectedBitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void AnhXa(){
        txtTenBS = findViewById(R.id.txtTenBSViewBS);
        txtTenBS.setText("Bác sĩ: "+nguoiDung.getTenNguoiDung());
        txtEmail = findViewById(R.id.txtEmailViewBS);
        txtEmail.setText("Thử điện tử: "+nguoiDung.getEmail_sdt());
        txtTenPhongKham = findViewById(R.id.txtTenPhongKhamViewBS);
        txtTenPhongKham.setText(phongKham.getTenPhongKham());
        txtChuyenKhoa = findViewById(R.id.txtChuyenKhoaViewBS);
        txtChuyenKhoa.setText("Chuyên khoa: "+phongKham.getChuyenKhoa());
        txtDiaChi = findViewById(R.id.txtDiaChiViewBS);
        txtDiaChi.setText("Địa chỉ: "+phongKham.getDiaChi());
        txtMoTa = findViewById(R.id.txtMoTaViewBS);
        txtMoTa.setText("Mô tả: "+phongKham.getMoTa());
        avatar = findViewById(R.id.PhongkhamAvatarViewBS);
        if(phongKham.getHinhAnh()!=null) {
            byte[] decodedString = Base64.decode(phongKham.getHinhAnh(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            avatar.setImageBitmap(decodedByte);
        }
        txtEdit = findViewById(R.id.txtDoiTTPK);
        bntDanhGia = findViewById(R.id.btnDanhGiaViewBS);
    }
}
