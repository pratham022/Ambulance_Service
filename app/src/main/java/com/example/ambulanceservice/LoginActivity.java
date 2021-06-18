package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;

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


public class LoginActivity extends AppCompatActivity implements AsyncResponseString{

    private EditText txtPhone, txtPassword;
    private Button btnLogin;
    private  String type, phone, pass;
    private String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if(sh.getString("phone", null) != null){
            Log.d(TAG, "Phone no found");
            System.out.println("Phone no found");
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else {
            Log.d(TAG, "Not found!!!!!!");
            System.out.println("Not found!!!!!!");
        }

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

        if (type.equals("")) {
            Toast.makeText(LoginActivity.this, "Please select the customer type(User/Driver)", Toast.LENGTH_SHORT).show();
            validType = false;
        } else {
            validType = true;
        }

        if (validPhone && validPass && validType) {
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("Password",pass);
            myEdit.apply();

            BackgroundLoginWorker backgroundLoginWorker = new BackgroundLoginWorker(this);
            backgroundLoginWorker.delegate = this;
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

    @Override
    public void processStringFinish(String s) {
        Log.d(TAG, s);
        Log.d(TAG, "In Process finish");
        try {
            JSONObject response = new JSONObject(s);
            if (response.getString("status").equals("1")) {
                JSONObject jsonObject =  response.getJSONObject("data");
                String id=jsonObject.getString("id");
                String phone=jsonObject.getString("phone");
                String name=jsonObject.getString("name");
                String address="";
                String email="";
                if (jsonObject.has("address") && !jsonObject.isNull("address")) {
                    // Do something with object.
                    address=jsonObject.getString("address");
                }
                if(jsonObject.has("email") && !jsonObject.isNull("email"))
                {
                    email=jsonObject.getString("email");
                }

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("id", id);
                myEdit.putString("phone", phone);
                myEdit.putString("name",name);
                myEdit.putString("address",address);
                myEdit.putString("email",email);
                myEdit.apply();

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), response.getString("data"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void signupClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}