package com.qlbh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityLogin extends AppCompatActivity {
    EditText editUserName, editPassWord;
    TextView tvDangKy, tvQuenPass;
    Button btnLogin, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = editUserName.getText().toString().trim();
                final String pass = editPassWord.getText().toString().trim();
                if (user.equals("") || pass.equals("")) {
                    AlertDialog.Builder dialogXoa = new AlertDialog.Builder(ActivityLogin.this);
                    dialogXoa.setMessage("Bạn chưa nhập UserName hoặc PassWord");
                    dialogXoa.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialogXoa.show();
                } else {
                    new login(user, pass).execute(new urlconfig().url + "login.php");
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassWord = (EditText) findViewById(R.id.editPassWord);
        tvDangKy = (TextView) findViewById(R.id.textDangKy);
        tvQuenPass = (TextView) findViewById(R.id.textQuyenPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnHuy = (Button) findViewById(R.id.btnHuy);
    }

    //class kiem tra login
    public class login extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        public String username, password;

        public login(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("username", username)
                    .addFormDataPart("password", password)
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .method("PUT", requestBody)
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

        @Override
        protected void onPostExecute(String s) {
            Log.d("Thông báo ", s);
            if (s.equals("success")) {
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(ActivityLogin.this);
                dialogXoa.setMessage("UserName hoặc PassWord không đúng");
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
}
