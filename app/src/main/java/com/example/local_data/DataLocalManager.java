package com.example.local_data;

import android.content.Context;

import com.example.model.NguoiDung;
import com.example.model.PhongKham;
import com.google.gson.Gson;

//lớp này quản lí dữ liệu local
public class DataLocalManager {
    private static final String ID_NGUOI_DUNG = "ID_NGUOI_DUNG";
    private static final String OBJECT_NGUOI_DUNG = "OBJECT_NGUOI_DUNG";
    private static final String ID_PHONG_KHAM = "ID_PHONG_KHAM";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context)
    {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance()
    {
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }
    public static void setIDNguoiDung(String idNguoiDung)
    {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ID_NGUOI_DUNG,idNguoiDung);
    }
    public static String getIDNguoiDung()
    {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ID_NGUOI_DUNG);
    }
    public static void setNguoiDung(NguoiDung nguoiDung)
    {
        Gson gson = new Gson();//gson để chuyển object sang json
        String strNguoiDung = gson.toJson(nguoiDung);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(OBJECT_NGUOI_DUNG,strNguoiDung);
    }
    public static NguoiDung getNguoiDung()
    {
        String jsonNguoiDung = DataLocalManager.getInstance().mySharedPreferences.getStringValue(OBJECT_NGUOI_DUNG);
        Gson gson = new Gson();
        NguoiDung nguoiDung = gson.fromJson(jsonNguoiDung,NguoiDung.class);
        return nguoiDung;
    }
    public static void setIDPhongKham(String idPhongKham)
    {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ID_PHONG_KHAM,idPhongKham);
    }
    public static String getIDPhongKham()
    {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ID_PHONG_KHAM);
    }
    /*public static void setPhongKham(PhongKham phongKham)
    {
        Gson gson = new Gson();//gson để chuyển object sang json
        String strPhongKham = gson.toJson(phongKham);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(OBJECT_NGUOI_DUNG,strPhongKham);
    }
    public static PhongKham getPhongKham()
    {
        String jsonNguoiDung = DataLocalManager.getInstance().mySharedPreferences.getStringValue(OBJECT_NGUOI_DUNG);
        Gson gson = new Gson();
        NguoiDung nguoiDung = gson.fromJson(jsonNguoiDung,NguoiDung.class);
        return nguoiDung;
    }*/
}
