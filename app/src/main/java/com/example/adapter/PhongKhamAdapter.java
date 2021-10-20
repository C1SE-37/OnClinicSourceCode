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

import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.example.onclinic.R;

import java.util.List;

public class PhongKhamAdapter extends ArrayAdapter<PhongKham> {
    //màn hình sử dụng layout này
    Activity activity;
    //layout cho từng dòng muốn hiển thị (thiết kế trong item xml)
    int resource;
    //danh sách nguồn để đưa vào layout
    List<PhongKham> objects;

    public PhongKhamAdapter(@NonNull Activity activity, int resource, @NonNull List<PhongKham> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //inflater dùng để build file xml đã vẽ thành code
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        //gán các thuộc tính của file item lịch sử đã vẽ vào biến
        TextView txtNgayKham = row.findViewById(R.id.txtNgayKham);
        TextView txtPhongKham = row.findViewById(R.id.txtTenPhongKham);
        TextView txtDiaChi = row.findViewById(R.id.txtDiaChi);
        TextView txtBacSi = row.findViewById(R.id.txtBacSi);
        TextView txtSDT = row.findViewById(R.id.txtSDT);
        TextView maCode = row.findViewById(R.id.txtCode);
        TextView txthinhThucKham = row.findViewById(R.id.txtHinhThuc);
        TextView tongTien = row.findViewById(R.id.txtTongTien);
        TextView btnChiTiet = row.findViewById(R.id.lbChiTiet);

        PhongKham phongKham = this.objects.get(position);
        txtPhongKham.setText(phongKham.getTenPhongKham());
        txtDiaChi.setText(phongKham.getDiaChi());
        txthinhThucKham.setText(phongKham.getHinhThucKham());

        return row;
    }
}
