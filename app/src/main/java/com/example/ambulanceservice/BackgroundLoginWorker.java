package com.example.ambulanceservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;

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

public class BackgroundLoginWorker extends AsyncTask<String, Void, String> {

    Context context;
    private String TAG = "BackgroundLoginWorker";

    public AsyncResponseString delegate = null;

    BackgroundLoginWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String phone = params[0];
        String pass = params[1];
        String type = params[2];
        String login_url = "http://192.168.43.182/login.php/";
        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String post_data = URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                    + "&&" +URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(pass, "UTF-8")
                    + "&&" +URLEncoder.encode("type", "UTF-8")+"="+URLEncoder.encode(type, "UTF-8");
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
            Log.d(TAG, result);

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        delegate.processStringFinish(s);
    }
}
