package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.local_data.DataLocalManager;
import com.example.model.PhongKham;
import com.example.onclinic.CapNhatSuatKham;
import com.example.onclinic.DatPhong;
import com.example.onclinic.DatPhong2;
import com.example.onclinic.R;

import java.util.List;

public class PhongKhamAdapter extends RecyclerView.Adapter<PhongKhamAdapter.PhongKhamViewHolder>{

    private List<PhongKham> phongKhamList;
    Context context;
    public IPhongKhamAdapter onClickListener;
    private int lastSelected = -1;

    public PhongKhamAdapter(List<PhongKham> phongKhamList, Context context, IPhongKhamAdapter onClickListener) {
        this.phongKhamList = phongKhamList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void release()
    {
        context = null;
    }

    @NonNull
    @Override
    public PhongKhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong_kham,parent,false);
        return new PhongKhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhongKhamViewHolder holder,final int position) {
        PhongKham phongKham = phongKhamList.get(position);
        if(phongKham == null) return;
        if(phongKham.getHinhAnh()!=null) {
            byte[] decodedString = Base64.decode(phongKham.getHinhAnh(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imgPhongKham.setImageBitmap(decodedByte);
        }
        holder.txtPhongKham.setText("Phòng khám "+phongKham.getTenPhongKham());
        holder.txtChuyenKhoa.setText("Chuyên khoa "+phongKham.getChuyenKhoa());
        //holder.ratingBar.setRating();
        holder.layoutPhongKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelected = position;
                onClickListener.clickPhongKham(phongKham);
                notifyDataSetChanged();
            }
        });
        if(lastSelected == position ){
            holder.layoutPhongKham.setBackgroundColor(Color.GRAY);
            holder.txtChuyenKhoa.setTextColor(Color.WHITE);
            holder.txtPhongKham.setTextColor(Color.WHITE);
        }
        else{
            holder.layoutPhongKham.setBackgroundColor(Color.WHITE);
            holder.txtChuyenKhoa.setTextColor(Color.BLACK);
            holder.txtPhongKham.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        if(phongKhamList!=null)
            return phongKhamList.size();
        return 0;
    }

    public interface IPhongKhamAdapter
    {
        void clickPhongKham(PhongKham phongKham);
    }

    public class PhongKhamViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPhongKham;
        TextView txtPhongKham, txtChuyenKhoa;
        RatingBar ratingBar;
        ConstraintLayout layoutPhongKham;

        public PhongKhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhongKham = itemView.findViewById(R.id.imgItemPhongKham);
            txtPhongKham = itemView.findViewById(R.id.txtItemPhongKham);
            txtChuyenKhoa = itemView.findViewById(R.id.txtItemChuyenKhoa);
            ratingBar = itemView.findViewById(R.id.item_rating_bar);
            layoutPhongKham = itemView.findViewById(R.id.layout_phongkham);
        }
    }
}
