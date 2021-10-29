package com.example.onclinic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.PhongKham;
import com.example.sqlhelper.NoteFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Test_Doc_DL extends AppCompatActivity {

    ImageView imgPK;
    TextView txtMa,txtTenPK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_doc_dl);
        addControls();
        docDuLieuFireBase();
    }

    private void docDuLieuFireBase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance(NoteFireBase.firebaseSource).getReference().child(NoteFireBase.PHONGKHAM);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    PhongKham pk = dataSnapshot.getValue(PhongKham.class);
                    pk.setIdPhongKham(dataSnapshot.getKey());
                    txtMa.setText(pk.getIdPhongKham());
                    if(pk.getHinhAnh()!=null) {
                        byte[] decodedString = Base64.decode(pk.getHinhAnh(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imgPK.setImageBitmap(decodedByte);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void addControls() {
        imgPK = findViewById(R.id.imagePK);
        txtMa = findViewById(R.id.maPK);
        txtTenPK = findViewById(R.id.txtTenPK);
    }
}