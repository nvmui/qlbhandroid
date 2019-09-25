package com.qlbh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityNhaphang extends AppCompatActivity {
Button btnLuu,btnThoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhaphang);
        AnhXa();
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void AnhXa() {
        btnLuu=(Button)findViewById(R.id.btnLuu);
        btnThoat=(Button)findViewById(R.id.btnThoat);
    }
}
