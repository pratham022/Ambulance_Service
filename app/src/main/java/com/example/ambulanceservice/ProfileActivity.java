package com.example.ambulanceservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
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

public class ProfileActivity extends AppCompatActivity implements AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if(sh.getString("phone", null) != null){
            TextView phone=(TextView)findViewById(R.id.phone);
            phone.setText(sh.getString("phone",null));
        }
       // fetchData();
//        BackgroundProfileWorker backgroundProfileWorker=new BackgroundProfileWorker(this);
//        backgroundProfileWorker.delegate=this;
//        backgroundProfileWorker.execute("tanayawankar58@gmail.com");

    }

    @Override
    public void processFinish(User output) {
        TextView username=(TextView)findViewById(R.id.username);
        TextView phone=(TextView)findViewById(R.id.phone);

        username.setText(output.getUsername());
        phone.setText(output.getPhone());

    }


//    public void fetchData(){
//        String login_url="http://10.0.2.2/userInfo.php";
//        try {
//
//            URL url = new URL(login_url);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setDoInput(true);
//            System.out.println("1");
//            OutputStream outputStream = httpURLConnection.getOutputStream();
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//            String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("tanayawankar58@gmail.com", "UTF-8");
//            System.out.println("2");
//            bufferedWriter.write(post_data);
//            bufferedWriter.flush();
//            bufferedWriter.close();
//            outputStream.close();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//            Log.d("Profile Activity","Reading data");
//            String result = "";
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                result += line;
//                System.out.println(line);
//            }
//            bufferedReader.close();
//            inputStream.close();
//            httpURLConnection.disconnect();
//
//            JSONObject response_data = new JSONObject(result);
//
//            if (response_data.getString("status").equals("1")) {
//                String id = response_data.getJSONObject("data").getString("id");
//                String name = response_data.getJSONObject("data").getString("name");
//                String phone = response_data.getJSONObject("data").getString("phone");
//                String email = response_data.getJSONObject("data").getString("email");
//                String address = response_data.getJSONObject("data").getString("address");
//
//                TextView username=(TextView)findViewById(R.id.username);
//
//                username.setText(name);
//
//                TextView phonenum=(TextView)findViewById(R.id.phone);
//                phonenum.setText(phone);
//
//
//
//            } else{
//                Toast.makeText(getApplicationContext(), response_data.getString("data"), Toast.LENGTH_LONG).show();
//            }
//
//
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
//        }
//    }
}