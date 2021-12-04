package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adapter.LichKhamBenhNhanAdapter;
import com.example.helper.NgayGio;
import com.example.local_data.DataLocalManager;
import com.example.local_data.MyApplication;
import com.example.model.LichKham;
import com.example.model.PhongKham;
import com.example.helper.NoteFireBase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LichKhamBenhNhan extends AppCompatActivity {

    private static final int NOTIFICATION_ID2 = 2;
    private static final String MY_ACTION_2 = "MY_ACTION_2";

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(MY_ACTION_2.equals(intent.getAction())){
                Toast.makeText(LichKhamBenhNhan.this,"Intent "+intent.getAction(),Toast.LENGTH_LONG).show();
                Bundle bundle = intent.getExtras();
                if(bundle == null) return;
                String tieuDe =  bundle.getString("TIEU_DE_THONG_BAO_2");
                String noiDung = bundle.getString("NOI_DUNG_THONG_BAO_2");
                guiThongBao(tieuDe, noiDung);
            }
        }
    };

    private RecyclerView rcvLichKham;
    private LichKhamBenhNhanAdapter lichKhamBenhNhanAdapter;
    private List<LichKham> listLichKham;
    private List<PhongKham> listPhongKham;//toàn bộ phòng khám trên firebase
    private List<PhongKham> listPhongKhamCoLichKham;//tìm được phòng khám có lịch khám bệnh nhân này thì thêm vào
    String idNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_kham_benh_nhan);
        idNguoiDung = DataLocalManager.getIDNguoiDung();
        addControls();
        layDanhSachLichKhamTuFireBase();
    }

    private void layDanhSachLichKhamTuFireBase() {
        docDanhSachPhongKhamTuFirebase(new ILichKhamBenhNhan() {
            @Override
            public void layDanhSachPhongKham(List<PhongKham> list) {
                for(PhongKham phong : list)
                {
                    String idPhongKham = phong.getIdPhongKham();
                    DatabaseReference refLichKham = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).
                            getReference(NoteFireBase.PHONGKHAM).child(idPhongKham).child(NoteFireBase.LICHKHAM);
                    refLichKham.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            LichKham lich = snapshot.getValue(LichKham.class);
                            if(idNguoiDung.equals(lich.getIdBenhNhan()) && lich.getTrangThai() == LichKham.DatLich)
                            {
                                listLichKham.add(lich);
                                listPhongKhamCoLichKham.add(phong);
                                Collections.sort(listLichKham, LichKham.LichKhamDateAsendingComparator);//sắp xếp ngày tăng dần
                            }
                            lichKhamBenhNhanAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            LichKham lich = snapshot.getValue(LichKham.class);
                            if(lich == null || listLichKham == null || listLichKham.isEmpty()) return;
                            for(int i = 0;i<listLichKham.size();i++)
                            {
                                if((lich.getIdLichKham()).equals(listLichKham.get(i).getIdLichKham())) {
                                    listLichKham.set(i, lich);
                                    Collections.sort(listLichKham, LichKham.LichKhamDateAsendingComparator);//sắp xếp ngày tăng dần
                                }
                            }
                            lichKhamBenhNhanAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void docDanhSachPhongKhamTuFirebase(ILichKhamBenhNhan iLichKhamBenhNhan)
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference(NoteFireBase.PHONGKHAM);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listPhongKham.clear();
                PhongKham phongKham = snapshot.getValue(PhongKham.class);
                if(phongKham!=null)
                {
                    listPhongKham.add(phongKham);
                }
                iLichKhamBenhNhan.layDanhSachPhongKham(listPhongKham);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface ILichKhamBenhNhan{
        void layDanhSachPhongKham(List<PhongKham> list);
    }

    private void guiThongBao(String tieuDe, String noiDung)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_app);
        Uri sound = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sound_notification_custom);

        Intent resultIntent = new Intent(this, LichKhamBenhNhan.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(NOTIFICATION_ID2, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID_2)
                .setContentTitle(tieuDe)
                .setContentText(noiDung)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(sound)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(noiDung))
                .setContentIntent(resultPendingIntent)
                .setSmallIcon(R.drawable.small_icon_notify)
                .build();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID2, notification);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(MY_ACTION_2);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        if(lichKhamBenhNhanAdapter != null)
            lichKhamBenhNhanAdapter.release();
    }

    private void xuLyThamGia(LichKham lichKham, PhongKham phongKham) {
        /*JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(lichKham.getIdLichKham().toString())
                .setUserInfo(new JitsiMeetUserInfo())
                .setWelcomePageEnabled(false).build();
        JitsiMeetActivity.launch(LichKhamBenhNhan.this,options);*/
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

        rcvLichKham = findViewById(R.id.rcvLichKhamBN);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LichKhamBenhNhan.this);
        rcvLichKham.setLayoutManager(layoutManager);
        listLichKham = new ArrayList<>();
        listPhongKham = new ArrayList<>();
        listPhongKhamCoLichKham = new ArrayList<>();

        lichKhamBenhNhanAdapter = new LichKhamBenhNhanAdapter(listLichKham, listPhongKhamCoLichKham, LichKhamBenhNhan.this, new LichKhamBenhNhanAdapter.ILichKhamBenhNhanAdapter() {
            @Override
            public void clickThamGia(LichKham lichKham, PhongKham phongKham) {
                xuLyThamGia(lichKham, phongKham);
            }
        });

        rcvLichKham.setAdapter(lichKhamBenhNhanAdapter);
    }
}