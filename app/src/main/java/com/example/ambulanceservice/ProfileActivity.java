package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ProfileActivity extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if(sh.getString("phone", null) != null){
            TextView phone=(TextView)findViewById(R.id.phone);
            phone.setText(sh.getString("phone",null));
            EditText editText=(EditText)findViewById(R.id.txtPhone);
            editText.setText(sh.getString("phone",null));
        }
        if(sh.getString("name",null)!=null)
        {
            TextView name=(TextView)findViewById(R.id.username);
            name.setText(sh.getString("name",null));
            EditText editText=(EditText)findViewById(R.id.txtName);
            editText.setText(sh.getString("name",null));
        }
        if(sh.getString("Password",null)!=null)
        {
            EditText editText=(EditText)findViewById(R.id.txtPassword);
            editText.setText(sh.getString("Password",null));
            EditText editText2=(EditText)findViewById(R.id.txtPassword2);
            editText2.setText(sh.getString("Password",null));
        }


    }

    @Override
    public void processFinish(User output) {
        TextView username=(TextView)findViewById(R.id.username);
        TextView phone=(TextView)findViewById(R.id.phone);

        username.setText(output.getUsername());
        phone.setText(output.getPhone());

    }


    public void EditDetails(View view)
    {

    }



}