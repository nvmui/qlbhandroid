package com.qlbh;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeleteMatHang extends AsyncTask<String, Void, String> {
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    public String ma;

    public DeleteMatHang(String maMH) {
        ma = maMH;
    }

    @Override
    protected String doInBackground(String... strings) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("MaMH", ma)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(strings[0])
                .method("DELETE", requestBody)
                .addHeader("Authorization", new urlconfig().token)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
