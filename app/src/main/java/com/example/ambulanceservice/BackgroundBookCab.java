package com.example.ambulanceservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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

import java.text.*;

public class BackgroundBookCab  extends AsyncTask<String, Void, Ride_Details> {

    Context context;

    Ride_Details ride_details;

    public AsyncResponseRideDetails delegate = null;

    BackgroundBookCab(View.OnClickListener onClickListener){

    }

    BackgroundBookCab(Context c)
    {
        context=c;
    }



    @Override
    protected Ride_Details doInBackground(String... strings) {

        String login_url= "https://quickcare.000webhostapp.com/book_cab.php";

        double src_lat1=Double.valueOf(strings[0]);
        double src_lng1=Double.valueOf(strings[1]);
        double dest_lat1=Double.valueOf(strings[2]);
        double dest_lng1=Double.valueOf(strings[3]);

        DecimalFormat df = new DecimalFormat("#.####");
        double src_lat = Double.parseDouble(df.format(src_lat1));
        double src_lng = Double.parseDouble(df.format(src_lng1));
        double dest_lat = Double.parseDouble(df.format(dest_lat1));
        double dest_lng = Double.parseDouble(df.format(dest_lng1));


        int user_id=Integer.valueOf(strings[4]);
        int payment_id=Integer.valueOf(strings[5]);

        try {

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            //  System.out.println("1");
            Log.e("Booking cab ride 1 ","Searching cab 1");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("src_lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(src_lat), "UTF-8")
                    + "&&" +URLEncoder.encode("src_lng", "UTF-8")+"="+URLEncoder.encode(String.valueOf(src_lng), "UTF-8")
                    + "&&" +URLEncoder.encode("dest_lat", "UTF-8")+"="+URLEncoder.encode(String.valueOf(dest_lat), "UTF-8")
                    + "&&" +URLEncoder.encode("dest_lng", "UTF-8")+"="+URLEncoder.encode(String.valueOf(dest_lng), "UTF-8")
                    + "&&" +URLEncoder.encode("user_id", "UTF-8")+"="+URLEncoder.encode(String.valueOf(user_id), "UTF-8")
                    + "&&" +URLEncoder.encode("payment_id", "UTF-8")+"="+URLEncoder.encode(String.valueOf(payment_id), "UTF-8");

            // System.out.println("2");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            Log.e("Booking cab ride ","Searching cab");
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

            Log.e("Result data", response_data.toString());

            if (response_data.getString("status").equals("1")) {
                String ride_id = response_data.getJSONObject("data").getString("ride_id");
                String cab_lat = response_data.getJSONObject("data").getString("cab_lat");
                String cab_lng = response_data.getJSONObject("data").getString("cab_lng");
                String cab_id = response_data.getJSONObject("data").getString("cab_id");
                String driver_name = response_data.getJSONObject("data").getString("driver_name");
                String driver_phone=response_data.getJSONObject("data").getString("driver_phone");
                String cab_no=response_data.getJSONObject("data").getString("cab_no");
                String fare=response_data.getJSONObject("data").getString("fare");
                String model_name=response_data.getJSONObject("data").getString("model_name");
                String model_description=response_data.getJSONObject("data").getString("model_description");

                ride_details=new Ride_Details(Integer.valueOf(ride_id),Double.valueOf(cab_lat),Double.valueOf(cab_lng),Integer.valueOf(cab_id),driver_name,driver_phone,cab_no,Integer.valueOf(fare),model_name,model_description);
                Log.e("ride_id",ride_id);
                Log.e("cab_lat",cab_lat);
                Log.e("cab_lng",cab_lng);
                Log.e("cab_id",cab_id);
                Log.e("driver_name",driver_name);
                Log.e("driver_phone",driver_phone);
                Log.e("cab_no",cab_no);
                Log.e("cab_fare",fare);
                Log.e("model_name",model_name);
                Log.e("model_description",model_description);

                return ride_details;


            } else{
                Log.e("Helllo", "Entered in else");
                return new Ride_Details();
            }


        }catch (Exception e){
            System.out.println("Cannot create connection");
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
        return new Ride_Details();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Ride_Details s) {
        super.onPostExecute(s);
        delegate.processStringFinish(s);

    }




}
