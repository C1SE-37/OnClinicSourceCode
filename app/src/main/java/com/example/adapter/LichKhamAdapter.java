package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.LichKham;
import com.example.onclinic.CapNhatSuatKham;
import com.example.onclinic.QuanLyPhongKham;
import com.example.onclinic.R;

import java.util.List;

public class LichKhamAdapter extends RecyclerView.Adapter<LichKhamAdapter.LichKhamViewHolder>{

    private List<LichKham> lichKhamList;
    Context context;

    public LichKhamAdapter(List<LichKham> lichKhamList, Context context) {
        this.lichKhamList = lichKhamList;
        this.context = context;
    }

    @NonNull
    @Override
    public LichKhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_kham,parent,false);
        return new LichKhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichKhamViewHolder holder, int position) {
        LichKham lichKham = lichKhamList.get(position);
        if(lichKham == null) return;
        holder.txtThoiGian.setText(lichKham.getGioKham()+" ngày "+lichKham.getNgayKham());
        holder.txtHinhThuc.setText(lichKham.getHinhThucKham());
        if(lichKham.getIdBenhNhan() == null)
            holder.txtTrangThai.setText("Chưa được đặt lịch");
        else holder.txtTrangThai.setText("Đã được đặt lịch");
        holder.layoutLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyClickItemSuatKham(lichKham);
            }
        });
    }

    private void xuLyClickItemSuatKham(LichKham lichKham) {
        Intent intent = new Intent(context, CapNhatSuatKham.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("OBJECT_LICH_KHAM",lichKham);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void release()
    {
        context = null;
    }

    @Override
    public int getItemCount() {
        if(lichKhamList!=null)
            return lichKhamList.size();
        return 0;
    }

    public class LichKhamViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhDaiDien;
        TextView txtTenBN,txtThoiGian,txtHinhThuc,txtTrangThai;
        ConstraintLayout layoutLichKham;

        public LichKhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhDaiDien = itemView.findViewById(R.id.imgAnhDaiDien);
            txtTenBN = itemView.findViewById(R.id.txtTenBN);
            txtThoiGian = itemView.findViewById(R.id.txtThoiGian);
            txtHinhThuc = itemView.findViewById(R.id.txtHinhThuc);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            layoutLichKham = itemView.findViewById(R.id.layout_lichkhambs);
        }
    }
}