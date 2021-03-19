package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText txtPhone, txtPassword;
    private Button btnLogin;
    private  String type, phone, pass;
    private String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        type = "";
        phone = "";
        pass = "";

    }

    public void loginClick(View view) {
        phone = txtPhone.getText().toString();
        pass = txtPassword.getText().toString();

        boolean validPhone = true, validPass = true, validType = true;
        if(phone.isEmpty()) {
            txtPhone.setError("Enter a valid phone number");
            validPhone = false;
        } else {
            txtPhone.setError(null);
            validPhone = true;
        }

        if (pass.isEmpty()) {
            txtPassword.setError("Please fill out this field");
            validPass = false;
        } else {
            txtPassword.setError(null);
            validPass = true;
        }

        if (type == "") {
            Toast.makeText(LoginActivity.this, "Please select the customer type(User/Driver)", Toast.LENGTH_SHORT).show();
            validType = false;
        } else {
            validType = true;
        }

        if (validPhone && validPass && validType) {
            BackgroundLoginWorker backgroundLoginWorker = new BackgroundLoginWorker(this);
            backgroundLoginWorker.execute(phone, pass, type);

        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioUser:
                if (checked)
                    type = "customer";
                break;
            case R.id.radioDriver:
                if (checked)
                    type = "driver";
                break;
        }
    }
}