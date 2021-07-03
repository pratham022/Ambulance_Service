package com.example.ambulanceservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import static android.content.Context.MODE_PRIVATE;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;
    static String driverName = "Driver Name: ";
    static String phone = "Phone No. ";
    static String cabNo = "Vehicle No: ";
    static String cabFare = "Fare: ";
    static String modelName = "Model Name: ";
    BackgroundCancelCab backgroundCancelCab;

    static TextView txtDriverName;
    static TextView txtDriverPhone;
    static TextView txtCabNo;
    static TextView txtCabFare;
    static TextView txtModelName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        Log.e("Dialog", "On Create View");
        Button button1 = v.findViewById(R.id.button1);
        Button button2 = v.findViewById(R.id.button2);
        txtDriverName = v.findViewById(R.id.driver_name);
        txtDriverPhone = v.findViewById(R.id.driver_phone);
        txtCabNo = v.findViewById(R.id.cab_no);
        txtCabFare = v.findViewById(R.id.cab_fare);
        txtModelName = v.findViewById(R.id.model_name);

        updateDetails();


        //call driver
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = LoginActivity.cxt.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String driver_num=sh.getString("driver_phone",null);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+driver_num));
                startActivity(intent);

                dismiss();
            }
        });

        //cancel ride
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context cxt=MainActivity.cxt;
               backgroundCancelCab=new BackgroundCancelCab(cxt);
               Log.e("context of mainactivity",String.valueOf(MainActivity.cxt));

                SharedPreferences sh = cxt.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String cab_id=sh.getString("cab_id",null);
                String ride_id=sh.getString("ride_id",null);
                backgroundCancelCab.execute(ride_id,cab_id);
                txtDriverName.setText("");
                txtCabFare.setText("");
                txtDriverPhone.setText("");
                txtCabNo.setText("");
                txtModelName.setText("");
                dismiss();
            }
        });


        return v;
    }

    public static void updateDetails() {

        txtDriverName.setText(driverName);
        txtDriverPhone.setText(phone);
        txtCabNo.setText(cabNo);
        txtCabFare.setText(cabFare);
        txtModelName.setText(modelName);
    }

    public static void resetDetails() {
        driverName = "Driver Name: ";
        phone = "Phone No. ";
        cabNo = "Vehicle No: ";
        cabFare = "Fare: ";
        modelName = "Model Name: ";
        updateDetails();
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}
