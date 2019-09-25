package com.qlbh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton imgBanHang, imgBaoCao, imgNhapHang, imgLoaiHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        imgNhapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityNhaphang.class);
                startActivity(intent);
            }
        });
        imgLoaiHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityBanhang.class);
                startActivity(intent);
            }
        });
        imgBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityNhapMathang.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        imgBanHang = (ImageButton) findViewById(R.id.imgBanHang);
        imgBaoCao = (ImageButton) findViewById(R.id.imgBaoCao);
        imgNhapHang = (ImageButton) findViewById(R.id.imgNhapHang);
        imgLoaiHang = (ImageButton) findViewById(R.id.imgLoaiHang);
    }
}
