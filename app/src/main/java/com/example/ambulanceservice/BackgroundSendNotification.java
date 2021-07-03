package com.example.ambulanceservice;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class BackgroundSendNotification extends AsyncTask<String, Void, String> {

    Context context;

    AlertDialog alertDialog;

    BackgroundSendNotification()
    {

    }

    BackgroundSendNotification(Context c){
        context=c;
    }


    @Override
    protected String doInBackground(String... strings) {

        String send_notification_url= context.getResources().getString(R.string.server_url)+"/sendSinglePush.php";

        String title=strings[0];
        String message=strings[1];
        String phone=strings[2];

        try{
            URL url = new URL(send_notification_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8")
                    + "&&" +URLEncoder.encode("message", "UTF-8")+"="+URLEncoder.encode(message, "UTF-8")
                    + "&&" +URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            JSONObject response_data = new JSONObject(result);

            String final_result;

            if (response_data.getString("status").equals("1")) {

                final_result="Booking canceled";


            }else
            {
                final_result="Unable to cancel ride";
            }

            return final_result;

        }catch(Exception e){
            Log.e("In catch of cancel","not able to cancel cab");

        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Herere", "notification send");



    }
}
