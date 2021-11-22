package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.DanhGia;
import com.example.model.LichKham;
import com.example.model.NguoiDung;
import com.example.onclinic.CapNhatSuatKham;
import com.example.onclinic.TrangCaNhanView;
import com.example.onclinic.VietDanhGia;
import com.example.onclinic.R;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.DanhGiaViewHolder> {

    private List<DanhGia> danhGiaList;
    DanhGia danhGia;

    public DanhGiaAdapter(List<DanhGia> danhGiaList ) {

        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public DanhGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_gia,parent,false);
        return new DanhGiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhGiaViewHolder holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);
        if (danhGia == null)
        {
            return;
        }
        holder.ratingbar.setRating(danhGia.getRating());
        holder.txtNhanXet.setText(danhGia.getNhanXet());
        holder.txtTenBN.setText(danhGia.getTenNguoiDungDG());
        if(danhGia.getAvatarNguoiDungDG()!=null) {
            byte[] decodedString = Base64.decode(danhGia.getAvatarNguoiDungDG(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.avatarBN.setImageBitmap(decodedByte);
        }
    }

    @Override
    public int getItemCount() {
        if (danhGiaList != null)
        {
            return danhGiaList.size();
        }
        return 0;
    }

    public class DanhGiaViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenBN, txtNhanXet;
        ImageView avatarBN;
        RatingBar ratingbar;
        ConstraintLayout layout_DanhGia;
        LinearLayout profileBN;

        public DanhGiaViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_DanhGia = itemView.findViewById(R.id.layout_DanhGia);
            txtTenBN = itemView.findViewById(R.id.txtItemDanhGia_TenBN);
            txtNhanXet = itemView.findViewById(R.id.txtItemDanhGia_Nhanxet);
            avatarBN = itemView.findViewById(R.id.imgItemDanhGia_avatarBN);
            ratingbar = itemView.findViewById(R.id.itemDanhGia_ratingbar);
            profileBN = itemView.findViewById(R.id.linearLayout_DanhGia_BN);
        }
    }
}
