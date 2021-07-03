package com.example.ambulanceservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class GetToken implements AsyncResponseString,Runnable {

    SharedPreferences sh;
    MyFirebaseInstanceIdService myFirebaseInstanceIDService;
    Context cxt;


    GetToken(){

        Log.e(" get token","now going to fetch token");
        myFirebaseInstanceIDService=new MyFirebaseInstanceIdService();
        myFirebaseInstanceIDService.onTokenRefresh();
        String phone="",token="";
        Context cxt=LoginActivity.cxt;
        sh= cxt.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if (sh.getString("phone", null) != null) {
            phone=sh.getString("phone", null);
        }
        if(sh.getString("token",null)!=null)
        {
            token=sh.getString("token",null);
        }

        Log.e("token",sh.getString("token",null));

        cxt=MainActivity.cxt;

        BackgroundSaveToken backgroundSaveToken=new BackgroundSaveToken(cxt);
        backgroundSaveToken.delegate=this;
        backgroundSaveToken.execute(phone,token);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void processStringFinish(String s) {
        try {
            JSONObject response = new JSONObject(s);
            String status = response.getString("status");
            if(status.equals("1")) {
                Log.e("Token updated successfully","status 1");
            }
            else {
                Log.e("Response TAG", "Some error");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

    }
}
