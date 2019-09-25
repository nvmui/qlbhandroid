package com.qlbh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityBanhang extends AppCompatActivity {
    EditText editKhachHang, editNoiDung;
    Spinner spnHinhThuc;
    Button btnDatHang, btnHuy;
    //    ListView lvMathang;
    String arr[] = {"Tiền mặt", "Chuyển khoản", "Ghi Nợ"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banhang);
        AnhXa();
        getMatHang();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spnHinhThuc.setAdapter(adapter);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class BackText extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream is = null;
            String rusul = "";

            return null;
        }
    }

    private void getMatHang() {
        final ListView lvMathang = (ListView) findViewById(R.id.listMatHang);
        //khai báo thư viện client
        OkHttpClient client = new OkHttpClient();
        Moshi moshi = new Moshi.Builder().build();
        Type mahangType = Types.newParameterizedType(List.class, Mathang.class);
        final JsonAdapter<List<Mathang>> jsonAdapter = moshi.adapter(mahangType);
        Request request = new Request.Builder()
                .url(new urlconfig().url + "listMatHang.php")
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", new urlconfig().token)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error aa", "Network Error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Lấy thông tin JSON trả về. Bạn có thể log lại biến json này để xem nó như thế nào.
                final String json = response.body().string();
                final List<Mathang> mathangs = jsonAdapter.fromJson(json);
                // Cho hiển thị lên RecyclerView.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvMathang.setAdapter(new MatHangAdapter(ActivityBanhang.this, R.layout.list_item_mathang, mathangs));
                    }
                });
            }
        });
    }

    private void AnhXa() {
        editKhachHang = (EditText) findViewById(R.id.editKhachHang);
        editNoiDung = (EditText) findViewById(R.id.editNoiDung);
        spnHinhThuc = (Spinner) findViewById(R.id.spnHinhThuc);
        btnDatHang = (Button) findViewById(R.id.btnDatHang);
        btnHuy = (Button) findViewById(R.id.btnHuy);
//        lvMathang = (ListView) findViewById(R.id.listMatHang);
    }
}
