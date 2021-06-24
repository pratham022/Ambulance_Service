package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements AsyncResponseString{

    private TextView txtPhone, txtPassword, txtPassword2, txtName;
    private String type = "";
    private String TAG = "RegisterActivity";
    private String phone, pass, pass2, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if(sh.getString("phone", null) != null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);
    }

    public void onRadioButtonRegisterClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioUser:
                if (checked)
                {
                    Button btn=(Button)findViewById(R.id.btnRegister);
                    btn.setText("REGISTER");
                    type = "customer";
                }

                break;
            case R.id.radioDriver:
                if (checked)
                {
                    Button btn=(Button)findViewById(R.id.btnRegister);
                    btn.setText("Next");
                    type = "driver";
                }

                break;
        }
    }

    public void registerClick(View view) {
        name = txtName.getText().toString();
        phone = txtPhone.getText().toString();
        pass = txtPassword.getText().toString();
        pass2 = txtPassword2.getText().toString();

        String emptyMsg = "Please fill out this field";
        boolean validPhone = false,
                validPass = false,
                validType = false,
                additionalFlag1 = false,
                additionalFlag2 = false;
        boolean validName = false;

        if (!name.isEmpty()) {
            validName = true;
            txtName.setError(null);
        } else {
            txtName.setError(emptyMsg);
        }

        if (!phone.isEmpty()) {
            validPhone = true;
            txtPhone.setError(null);
        } else {
            txtPhone.setError(emptyMsg);
        }

        if (!pass.isEmpty()) {
            additionalFlag1 = true;
            txtPassword.setError(null);
        } else {
            txtPassword.setError(emptyMsg);
        }

        if (!pass2.isEmpty()) {
            additionalFlag2 = true;
            txtPassword2.setError(null);
        } else {
            txtPassword2.setError(emptyMsg);
        }

        if (additionalFlag1==true && additionalFlag2==true) {
            if (pass.equals(pass2)) {
                validPass = true;
                txtPassword.setError(null);
                txtPassword2.setError(null);
            } else {
                txtPassword.setError(emptyMsg);
                txtPassword2.setError(emptyMsg);
            }
        } else {
            validPass = false;
        }

        if (!type.equals("")) {
            validType = true;
        }

        Log.d(TAG, "Helllllo");
        if (validName && validPhone && additionalFlag1 && additionalFlag2 && validPass && validType) {
            if(type=="driver")
            {
                System.out.println("Here");
                Intent intent=new Intent(this,RegisterDriver.class);
                intent.putExtra("name",name);
                intent.putExtra("phone",phone);
                intent.putExtra("password",pass);
                startActivity(intent);

            }
            else
            {
                Log.d(TAG, "Registering you in!");
                BackgroundRegisterWorker backgroundRegisterWorker = new BackgroundRegisterWorker(this);
                backgroundRegisterWorker.delegate = this;
                backgroundRegisterWorker.execute(phone, name, pass, type);
            }

        } else {
            Log.d(TAG, "SOme Error");
            Toast.makeText(this, "Some Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processStringFinish(String s) {
        Log.d(TAG, s);
        Log.d(TAG, "In Process Register finish");
        try {
            JSONObject response = new JSONObject(s);
            if (response.getString("status").equals("1")) {



                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finishAffinity();
            } else {
                Toast.makeText(getApplicationContext(), response.getString("data"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}