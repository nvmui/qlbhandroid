package com.qlbh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityNhapMathang extends AppCompatActivity {
    EditText editMaMH, editTenhang, editDongia, editSoluong, editGhichu;
    Spinner spnDVT;
    Button btnThemMoi, btnCapNhat, btnThoat;
    ListView lvMatHang;
    String arr[] = {"Lon", "Chai", "Thung", "Ly", "Lốc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_mathang);
        AnhXa();
//        final String matg = tacGia.MaTG;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        //Thiết lập adapter cho Spinner
        spnDVT.setAdapter(adapter);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getMatHang();
        btnThemMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenhang, sl, dg, ghichu, dvt;
                tenhang = editTenhang.getText().toString().trim();
                sl = editSoluong.getText().toString().trim();
                dg = editDongia.getText().toString().trim();
                ghichu = editGhichu.getText().toString().trim();
                dvt = spnDVT.getSelectedItem().toString().trim();
                new AddMatHang(tenhang, sl, dg, ghichu, dvt).execute(new urlconfig().url + "insertMatHang.php");
                getMatHang();
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenhang, sl, dg, ghichu, dvt, mahang;
                mahang = editMaMH.getText().toString().trim();
                tenhang = editTenhang.getText().toString().trim();
                sl = editSoluong.getText().toString().trim();
                dg = editDongia.getText().toString().trim();
                ghichu = editGhichu.getText().toString().trim();
                dvt = spnDVT.getSelectedItem().toString().trim();
                String kt= String.valueOf(new UpdateMatHang(mahang, tenhang, sl, dvt, dg, ghichu).execute(new urlconfig().url + "updateMatHang.php"));
                if(kt.equals("success")){
                getMatHang();}else {}
            }
        });
    }

    public void getMatHang() {
        final ListView lvMathang = (ListView) findViewById(R.id.lvMatHang);
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
                        lvMathang.setAdapter(new NhapMatHang(ActivityNhapMathang.this, R.layout.list_mat_hang, mathangs));
                    }
                });
            }
        });
    }

    //Thêm mới mặt hàng
    class AddMatHang extends AsyncTask<String, Void, String> {
        String editTenMH, editSL, editDG, editGhichu, spinnerDVT;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        public AddMatHang(String editTenMH, String editSL, String editDG, String editGhichu, String spinnerDVT) {
            this.editTenMH = editTenMH;
            this.editSL = editSL;
            this.editDG = editDG;
            this.editGhichu = editGhichu;
            this.spinnerDVT = spinnerDVT;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("TenMH", editTenMH)
                    .addFormDataPart("SL", editSL)
                    .addFormDataPart("DonGia", editDG)
                    .addFormDataPart("DVT", spinnerDVT)
                    .addFormDataPart("GhiChu", editGhichu)
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .addHeader("Authorization", new urlconfig().token)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d("Thông báo ", s);
            AlertDialog.Builder dialogXoa = new AlertDialog.Builder(ActivityNhapMathang.this);
            if(s.equals("success"))
            {
                dialogXoa.setMessage("Thêm mới mặt hàng thành công");
                dialogXoa.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialogXoa.show();
                getMatHang();
            }else {
                dialogXoa.setMessage("Thêm mới mặt hàng không thành công");
                dialogXoa.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialogXoa.show();
            }
            super.onPostExecute(s);
        }
    }

    //Khai báo ánh xạ
    private void AnhXa() {
        editMaMH = (EditText) findViewById(R.id.editMaMH);
        editTenhang = (EditText) findViewById(R.id.edtTenhang);
        editDongia = (EditText) findViewById(R.id.editDongia);
        editSoluong = (EditText) findViewById(R.id.edtSoluong);
        editGhichu = (EditText) findViewById(R.id.editGhiChu);
        lvMatHang = (ListView) findViewById(R.id.lvMatHang);
        spnDVT = (Spinner) findViewById(R.id.spnDVT);
        btnThemMoi = (Button) findViewById(R.id.btnThem);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        btnThoat = (Button) findViewById(R.id.btnThoat);
    }
}
