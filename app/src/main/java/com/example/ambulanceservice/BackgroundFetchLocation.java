package com.example.ambulanceservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;

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
import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;

public class BackgroundFetchLocation  extends AsyncTask<String, Void, String> {

    Context cxt;

    public AsyncResponseString delegate = null;

    BackgroundFetchLocation()
    {

    }

    BackgroundFetchLocation(Context cxt)
    {
        this.cxt=cxt;
    }
    @Override
    protected String doInBackground(String... strings) {

        String get_location_url=cxt.getResources().getString(R.string.server_url)+"/get_cab_location.php";
        String cab_id=strings[0];
        try {

            URL url = new URL(get_location_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //  System.out.println("1");
            Log.e("Fetching Cab Location ","get driver");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("cab_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(cab_id), "UTF-8");

            // System.out.println("2");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
                // System.out.println(line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            JSONObject response_data = new JSONObject(result);

           // Log.e("Result data", response_data.toString());

            if (response_data.getString("status").equals("1")) {
                String driver_lat = response_data.getJSONObject("data").getString("cab_lat");
                String driver_lng= response_data.getJSONObject("data").getString("cab_lng");

                return driver_lat+" "+driver_lng;
            } else{
                Log.e("In else", "of get driver location");
                return null;
            }


        }catch (Exception e){
            System.out.println("Cannot create connection");
            e.printStackTrace();
//            Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }

        return null;
    }

    protected void onPostExecute(String s)
    {
        delegate.processStringFinish(s);


    }


}
