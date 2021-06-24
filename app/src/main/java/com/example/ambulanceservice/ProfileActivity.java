package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
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

public class ProfileActivity extends AppCompatActivity implements AsyncResponseString{


    String name,password1,email,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if(sh.getString("phone", null) != null){
            TextView phone=(TextView)findViewById(R.id.phone);
            phone.setText(sh.getString("phone",null));

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
        if(sh.getString("address",null)!=null)
        {
            EditText editText=(EditText)findViewById(R.id.txtAddress);
            editText.setText(sh.getString("address",null));
        }
        if(sh.getString("email",null)!=null)
        {
            EditText editText=(EditText)findViewById(R.id.txtEmail);
            editText.setText(sh.getString("email",null));
        }


    }


    public void EditDetails(View view)
    {

        TextView nameText=(TextView)findViewById(R.id.txtName);
        name=String.valueOf(nameText.getText());
        TextView passwordText=(TextView)findViewById(R.id.txtPassword);
        TextView passwordText2=(TextView)findViewById(R.id.txtPassword2);
//        if(String.valueOf(passwordText.getText())!=String.valueOf(passwordText2.getText()))
//        {
//            Toast.makeText(this, "Password not same!", Toast.LENGTH_SHORT).show();
//
//        }
//        else
//        {
            password1=String.valueOf(passwordText.getText());


            TextView addressText=(TextView)findViewById(R.id.txtAddress);
            address=String.valueOf(addressText.getText());
            TextView emailText=(TextView)findViewById(R.id.txtEmail);
            email=String.valueOf(emailText.getText());

            Log.e("Address",address);
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

            BackgroundEditUserWorker backgroundEditUserWorker = new BackgroundEditUserWorker(this);
            backgroundEditUserWorker.delegate = this;
            backgroundEditUserWorker.execute(sh.getString("phone", null) ,name, email, address,password1);

        //}


    }

    public void processStringFinish(String s) {
        try {
            JSONObject response = new JSONObject(s);
            if (response.getString("status").equals("1")) {

                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();
                myEdit.putString("name",name);
                myEdit.putString("Password",password1);
                myEdit.putString("address",address);
                myEdit.putString("email",email);

               // Toast.makeText(getApplicationContext(), response.getString("details updated successfully"), Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);


            } else {
                Toast.makeText(getApplicationContext(), response.getString("details not updated"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}