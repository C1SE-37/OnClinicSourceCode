package com.example.onclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;

public class DanhGia extends AppCompatActivity {
    EditText edt_BinhLuan;
    Button bnt_SubmitDanhGia;
    RatingBar rating_bar;
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        AnhXa();
        Firebase.setAndroidContext(this);

        String uniqueID =
        Settings.Secure.getString(getApplicationContext().getContentResolver(),
        Settings.Secure.ANDROID_ID);

        firebase = new Firebase("https://onclinic-180ee-default-rtdb.asia-southeast1.firebasedatabase.app/RatingUsers");

        bnt_SubmitDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String binhluan = edt_BinhLuan.getText().toString();
                float rating = rating_bar.getRating();

                Firebase child_id = firebase.child("ID");
                child_id.setValue(uniqueID);
                bnt_SubmitDanhGia.setEnabled(true);

                //binh luan
                Firebase child_feedback = firebase.child("Feedback");
                child_feedback.setValue(binhluan);
                edt_BinhLuan.setError(null);
                bnt_SubmitDanhGia.setEnabled(true);

                //rating
                Firebase child_rating = firebase.child("Rating");
                child_rating.setValue(rating);

                rating_bar.setContentDescription("Bạn đánh giá " +rating+" sao!");
                bnt_SubmitDanhGia.setEnabled(true);
//                if (rating_bar.isClickable())
//                {
//                    rating_bar.setContentDescription("Bạn đánh giá " +rating+" sao!");
//                    bnt_SubmitDanhGia.setEnabled(true);
//                }
//                else
//                    {
//                        rating_bar.setContentDescription("Bạn chưa đánh giá!");
//                        rating_bar.setEnabled(false);
//
//                    }
            }
        });
    }



    private void AnhXa(){
        rating_bar = findViewById(R.id.rating_bar);
        edt_BinhLuan = findViewById(R.id.edt_BinhLuan);
        bnt_SubmitDanhGia = findViewById(R.id.btn_SubmitDanhGia);
    }
}