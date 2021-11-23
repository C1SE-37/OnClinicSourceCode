package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.LichSu;
import com.example.model.NguoiDung;
import com.example.onclinic.ChiTietLichSu;
import com.example.onclinic.NhapDonThuoc;
import com.example.onclinic.R;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class LichSuAdapter extends RecyclerView.Adapter<LichSuAdapter.LichKhamViewHolder>{

    private List<LichSu> lichSuList;
    Context context;

    public LichSuAdapter(List<LichSu> lichSuList, Context context) {
        this.lichSuList = lichSuList;
        this.context = context;
    }

    @NonNull
    @Override
    public LichKhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su,parent,false);
        return new LichKhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichKhamViewHolder holder, int position) {
        LichSu lichSu = lichSuList.get(position);
        if(lichSu == null) return;
        holder.txtNgayKham.setText("Ngày khám: "+lichSu.getLichKham().getNgayKham());
        holder.txtTenPhongKham.setText("Phòng khám "+lichSu.getPhongKham().getTenPhongKham());
        holder.txtDiaChi.setText("Địa chỉ: "+lichSu.getPhongKham().getDiaChi());
        holder.txtHinhThucKham.setText("Hình thức: "+lichSu.getLichKham().getHinhThucKham());
        holder.txtTongTien.setText("Tổng tiền: ");
        String idBacSi = lichSu.getPhongKham().getIdBacSi();
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference()
                .child(NoteFireBase.NGUOIDUNG).child(NoteFireBase.BACSI).child(idBacSi);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NguoiDung nguoiDung = snapshot.getValue(NguoiDung.class);
                holder.txtTenBacSi.setText("Bác sĩ: "+nguoiDung.getTenNguoiDung());
                holder.txtEmailBacSi.setText("Email: "+nguoiDung.getEmail_sdt());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.txtChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietLichSu.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHI_TIET_LICH_SU",lichSu);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lichSuList!=null)
            return lichSuList.size();
        return 0;
    }

    public void release()
    {
        context = null;
    }

    public class LichKhamViewHolder extends RecyclerView.ViewHolder{

        TextView txtNgayKham, txtTenPhongKham, txtDiaChi, txtTenBacSi, txtEmailBacSi, txtHinhThucKham, txtTongTien;
        TextView txtChiTiet;

        public LichKhamViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNgayKham = itemView.findViewById(R.id.txtNgayKhamItemLichSu);
            txtTenPhongKham = itemView.findViewById(R.id.txtTenPhongKhamItemLichSu);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChiItemLichSu);
            txtTenBacSi = itemView.findViewById(R.id.txtBacSiItemLichSu);
            txtEmailBacSi = itemView.findViewById(R.id.txtEmailItemLichSu);
            txtHinhThucKham = itemView.findViewById(R.id.txtHinhThucItemLichSu);
            txtTongTien = itemView.findViewById(R.id.txtTongTienItemLichSu);

            txtChiTiet = itemView.findViewById(R.id.txtChiTietItemLichSu);
        }
    }
}
