package com.example.user.workshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    private Button btLogin;
    private Button btRegister;
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        setEvent();
    }

    private void setEvent() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidate()) {
                    new login(etUsername.getText().toString(),
                            etPassword.getText().toString())
                            .execute();
                } else {
                    Toast.makeText(Login.this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }

    private boolean setValidate() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (username.isEmpty()) {
            return false;
        }

        if (password.isEmpty()) {
            return false;
        }

        return true;
    }

    private class login extends AsyncTask<Void, Void, String> {

        private String username;
        private String password;

        public login(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/login.php")
                    .post(requestBody)
                    .build();
            
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject rootObj = new JSONObject(s);
                if (rootObj.has("result")) {
                    JSONObject resultObj = rootObj.getJSONObject("result");

                    if (resultObj.getInt("result") == 1) {
                        Toast.makeText(Login.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                        etPassword.setText("");
                        Intent i = new Intent(Login.this, NewsList.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(Login.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
            }
        }
    }
}
