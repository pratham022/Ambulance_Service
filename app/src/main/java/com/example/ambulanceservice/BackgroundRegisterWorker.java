package com.example.ambulanceservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundRegisterWorker extends AsyncTask<String, Void, String> {

    Context context;
    private String TAG = "BackgroundRegisterWorker";
    public AsyncResponseString delegate = null;

    BackgroundRegisterWorker(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String phone = params[0];
        String name = params[1];
        String pass1 = params[2];
        String type = params[3];
        String register_url = "";


        if (type.equals("customer"))
            register_url = R.string.server_url + "user_register.php";
        else
            register_url = R.string.server_url + "driver_register.php";

        if(type.equals("customer"))
        {

            Log.d(TAG, register_url);
            try {
                Log.d(TAG, "Register URL: "+register_url);
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = "";
                if (type.equals("customer")) {
                    post_data = URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                            + "&&" +URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")
                            + "&&" +URLEncoder.encode("pass", "UTF-8")+"="+URLEncoder.encode(pass1, "UTF-8");
                }
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


        }
        else
        {
            System.out.println("here in customer");
            String license=params[4];
            String res_address=params[5];
            String date=params[6];

            System.out.println(pass1);

            Log.d(TAG, register_url);
            try {
                Log.d(TAG, "Register URL: "+register_url);
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = "";
                    post_data = URLEncoder.encode("phone", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                            + "&&" +URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(name, "UTF-8")
                            + "&&" +URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(pass1, "UTF-8")
                            +"&&"+URLEncoder.encode("license_no", "UTF-8")+"="+URLEncoder.encode(license, "UTF-8")
                            +"&&"+URLEncoder.encode("expiry_date", "UTF-8")+"="+URLEncoder.encode(date, "UTF-8")
                            +"&&"+URLEncoder.encode("address", "UTF-8")+"="+URLEncoder.encode(res_address, "UTF-8");


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
                   // System.out.println(line);
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


        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        delegate.processStringFinish(s);
    }
}
