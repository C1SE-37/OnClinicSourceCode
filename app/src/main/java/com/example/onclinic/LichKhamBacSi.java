package com.example.onclinic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.LichKhamBacSiAdapter;
import com.example.local_data.DataLocalManager;
import com.example.local_data.MyApplication;
import com.example.model.LichKham;
import com.example.model.NguoiDung;
import com.example.helper.NgayGio;
import com.example.helper.NoteFireBase;
import com.example.model.PhongKham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LichKhamBacSi extends AppCompatActivity {

    private static final String MY_ACTION_2 = "MY_ACTION_2";
    private static final int NOTIFICATION_ID2 = 2;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*if(MY_ACTION_2.equals(intent.getAction())){
                Toast.makeText(LichKhamBacSi.this,"Đã gửi yêu cầu đến bệnh nhân",Toast.LENGTH_SHORT).show();
            }*/
        }
    };

    private RecyclerView rcvLichKham;
    private LichKhamBacSiAdapter lichKhamBacSiAdapter;
    private List<LichKham> listLichKham;

    String idPhongKham;
    PhongKham phongKham;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_kham_bac_si);
        idPhongKham = DataLocalManager.getIDPhongKham();
        phongKham = DataLocalManager.getPhongKham();
        addControls();
        layDanhSachLichKhamTuFireBase();
    }

    private void layDanhSachLichKhamTuFireBase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        DatabaseReference refSuatKham = myRef.child(idPhongKham).child(NoteFireBase.LICHKHAM);
        refSuatKham.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listLichKham.clear();
                for(DataSnapshot data : snapshot.getChildren())
                {
                    LichKham lichKham = data.getValue(LichKham.class);
                    try {
                        Date ngayFirebase = NgayGio.ConvertStringToDate(lichKham.getNgayKham());
                        Date ngayHienTai = NgayGio.GetDateCurrent();
                        //so sánh ngày trong lịch và hiện tại để thêm vào lịch khám và phải ở trạng thái chưa khám
                        if(ngayFirebase.getTime() >= ngayHienTai.getTime() && lichKham.getTrangThai() <= LichKham.DatLich)
                        {
                            listLichKham.add(lichKham);
                            Collections.sort(listLichKham, LichKham.LichKhamDateAsendingComparator);//sắp xếp ngày tăng dần
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                lichKhamBacSiAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LichKhamBacSi.this,"Lỗi đọc dữ liệu",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MY_ACTION_2);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(lichKhamBacSiAdapter != null)
            lichKhamBacSiAdapter.release();
    }

    private void xuLyBatDau(LichKham lichKham, NguoiDung nguoiDung) {
        /*JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(lichKham.getIdLichKham().toString())
                .setWelcomePageEnabled(false).build();
        JitsiMeetActivity.launch(LichKhamBacSi.this,options);*/

        guiBroadcast("Phòng khám "+phongKham.getTenPhongKham(),
                "Buổi khám vào "+lichKham.getGioKham()+" ngày "+lichKham.getNgayKham()+" đã bắt đầu");
    }

    private void guiBroadcast(String tieuDe, String noiDung) {
        Intent intent = new Intent(MY_ACTION_2);
        Bundle bundle = new Bundle();
        bundle.putString("TIEU_DE_THONG_BAO_2",tieuDe);
        bundle.putString("NOI_DUNG_THONG_BAO_2",noiDung);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    private void addControls() {
        URL server;
        try{
            server =new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions=
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(server)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        rcvLichKham = findViewById(R.id.rcvLichKhamBS);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LichKhamBacSi.this);
        rcvLichKham.setLayoutManager(layoutManager);

        listLichKham = new ArrayList<>();
        lichKhamBacSiAdapter = new LichKhamBacSiAdapter(listLichKham, LichKhamBacSi.this, new LichKhamBacSiAdapter.ILichKhamBacSiAdapter() {
            @Override
            public void clickBatDau(LichKham lichKham, NguoiDung nguoiDung) {
                xuLyBatDau(lichKham, nguoiDung);
            }
        });

        rcvLichKham.setAdapter(lichKhamBacSiAdapter);
    }
}
