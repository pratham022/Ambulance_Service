package com.example.ambulanceservice;

public class Ride_Details {

    public int ride_id;
    public double cab_lat;
    public double cab_lng;
    public int cab_id;
    public String driver_name;
    public String driver_phone;
    public String cab_no;
    public int cab_fare;
    public String model_name;
    public String model_description;

    Ride_Details(){

    }

    Ride_Details(int ride_id,double cab_lat,double cab_lng,int cab_id,String driver_name,String driver_phone,String cab_no,int cab_fare,String model_name,String model_description)
    {
        this.ride_id=ride_id;
        this.cab_lat=cab_lat;
        this.cab_lng=cab_lng;
        this.cab_id=cab_id;
        this.driver_name=driver_name;
        this.driver_phone=driver_phone;
        this.cab_no=cab_no;
        this.cab_fare=cab_fare;
        this.model_name=model_name;
        this.model_description=model_description;
    }

    int getCab_id(){
        return cab_id;
    }

    int getRide_id(){
        return ride_id;
    }

    String getCab_no()
    {
        return cab_no;
    }

    int getCab_fare()
    {
        return cab_fare;
    }

    double getCab_lat()
    {
        return cab_lat;
    }
    double getCab_lng()
    {
        return cab_lng;
    }
     String getDriver_name()
     {
         return driver_name;
     }
     String getDriver_phone()
     {
         return driver_phone;
     }

     String getModel_name()
     {
         return model_name;
     }

     String getModel_description()
     {
         return model_description;
     }

}
