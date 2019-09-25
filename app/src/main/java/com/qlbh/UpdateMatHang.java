package com.qlbh;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateMatHang extends AsyncTask<String, Void, String> {
    String maMH, editTenMH, editSL, spinnerDVT, editDG, editGhichu;
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    ActivityNhapMathang conten;

    public UpdateMatHang(String maMH, String editTenMH, String editSL, String spinnerDVT, String editDG, String editGhichu) {
        this.maMH = maMH;
        this.editTenMH = editTenMH;
        this.editSL = editSL;
        this.spinnerDVT = spinnerDVT;
        this.editDG = editDG;
        this.editGhichu = editGhichu;
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("MaMH", maMH)
                .addFormDataPart("TenMH", editTenMH)
                .addFormDataPart("SL", editSL)
                .addFormDataPart("DonGia", editDG)
                .addFormDataPart("DVT", spinnerDVT)
                .addFormDataPart("GhiChu", editGhichu)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(strings[0])
                .method("PUT", requestBody)
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
        super.onPostExecute(s);
    }
}
