package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.model.LichSu;
import com.example.model.PhongKham;
import com.example.onclinic.R;

import java.util.List;

public class LichSuAdapter extends ArrayAdapter<LichSu> {

    Activity activity;
    int resource;
    List<LichSu> objects;
    public LichSuAdapter(@NonNull Activity activity, int resource, @NonNull List<LichSu> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtNgayKham = row.findViewById(R.id.txtNgayKham);
        TextView txtPhongKham = row.findViewById(R.id.txtTenPhongKham);
        TextView txtDiaChi = row.findViewById(R.id.txtDiaChi);
        TextView txtBacSi = row.findViewById(R.id.txtBacSi);
        TextView txtSDT = row.findViewById(R.id.txtSDT);
        TextView maCode = row.findViewById(R.id.txtCode);
        TextView txthinhThucKham = row.findViewById(R.id.txtHinhThuc);
        TextView txttongTien = row.findViewById(R.id.txtTongTien);
        TextView btnChiTiet = row.findViewById(R.id.lbChiTiet);

        LichSu lichSu = this.objects.get(position);
        /*txtPhongKham.setText(lichSu.getPhongKham().getTenPhongKham().toString());
        txtDiaChi.setText(lichSu.getPhongKham().getDiaChi().toString());
        txtBacSi.setText(lichSu.getNguoiKham().toString());
        txthinhThucKham.setText(lichSu.getPhongKham().getHinhThucKham().toString());*/
        txttongTien.setText(lichSu.getTongTien());
        return row;
    }
}
