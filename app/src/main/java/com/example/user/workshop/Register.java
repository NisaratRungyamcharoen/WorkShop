package com.example.user.workshop;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
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

public class Register extends AppCompatActivity {

    private EditText etDisplayNameReg;
    private EditText etUsernameReg;
    private EditText etPasswordReg;
    private EditText etRePasswordReg;
    private Button btRegisterReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDisplayNameReg = (EditText) findViewById(R.id.etDisplayNameReg);
        etUsernameReg = (EditText) findViewById(R.id.etUsernameReg);
        etPasswordReg = (EditText) findViewById(R.id.etPasswordReg);
        etRePasswordReg = (EditText) findViewById(R.id.etRePasswordReg);
        btRegisterReg = (Button) findViewById(R.id.btRegisterReg);

        setEvent();
    }

    private boolean setValidate() {
        String username = etUsernameReg.getText().toString();
        String password = etPasswordReg.getText().toString();
        String Repassword = etRePasswordReg.getText().toString();
        String displayName = etDisplayNameReg.getText().toString();
        if (username.isEmpty()) {
            return false;
        }

        if (password.isEmpty()) {
            return false;
        }

        if (Repassword.isEmpty()) {
            return false;
        }

        if (!password.equals(Repassword)) {
            return false;
        }

        if (displayName.isEmpty()) {
            return false;
        }

        return true;
    }

    private void setEvent() {
        btRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidate()) {
                    new register(etUsernameReg.getText().toString(),
                            etPasswordReg.getText().toString(),
                            etRePasswordReg.getText().toString(),
                            etDisplayNameReg.getText().toString())
                            .execute();
                } else {
                    Toast.makeText(Register.this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class register extends AsyncTask<Void, Void, String> {
        // void คือเราไม่ต้องการรับส่งค่าอะไรเลย คือให้เป็นค่าว่างเปล่า
        //Pre>Back>Post

        private String username;
        private String password;
        private String Repassword;
        private String displayName;

        public register(String username, String password, String repassword, String displayName) {
            this.username = username;
            this.password = password;
            this.Repassword = repassword;
            this.displayName = displayName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //เขียนการเชื่องต่อ Server ในนี้
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("passowrd_con", Repassword)
                    .add("display_name", displayName)
                    .build();

            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
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
                        Toast.makeText(Register.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Register.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {

            }
        }
    }
}
