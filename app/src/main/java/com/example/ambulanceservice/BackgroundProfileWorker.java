package com.example.ambulanceservice;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class BackgroundProfileWorker extends AsyncTask<String, Void, User> {


    Context context;
    AlertDialog alertDialog;
    User u;

    public AsyncResponse delegate=null;

    BackgroundProfileWorker(Context cxt){
        context=cxt;
    }

    @Override
    protected User doInBackground(String... strings) {
        String login_url= context.getResources().getString(R.string.server_url)+"/userInfo.php";

        String email=strings[0];

        try {

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
          //  System.out.println("1");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
           // System.out.println("2");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            Log.d("Profile Activity","Reading data");
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

            if (response_data.getString("status").equals("0")) {
                String id1 = response_data.getJSONObject("data").getString("id");
                String name1 = response_data.getJSONObject("data").getString("name");
                String phone1 = response_data.getJSONObject("data").getString("phone");
                String email1 = response_data.getJSONObject("data").getString("email");
                String address1 = response_data.getJSONObject("data").getString("address");

                int id2=Integer.parseInt(id1);

                u=new User(id2,name1,phone1,email1,address1);

                return u;


            } else{
                return new User();
            }


        }catch (Exception e){
            Toast.makeText(context.getApplicationContext(), "error", Toast.LENGTH_LONG).show();
        }
        return new User();
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(User s) {
       // super.onPostExecute(s);
        System.out.println(s.getUsername()+" "+s.getPhone());
        //alertDialog.setMessage(s);
        //alertDialog.show();
        delegate.processFinish(s);

    }

    @Override
    protected void onPreExecute() {

        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Loading Your  Details");
    }
}
