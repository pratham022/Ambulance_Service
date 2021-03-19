package com.example.ambulanceservice;

public class User {

    private int Id;
    private String Username;
    private String Phone;
    private String Email;
    private String Address;

    User(){
        Id=0;
        Username="";
        Phone="";
        Email="";
        Address="";
    }

    User(int id,String username,String phone,String email,String address){
        Id=id;
        Username=username;
        Phone=phone;
        Email=email;
        Address=address;
    }

    String getUsername(){
        return Username;
    }

    String getPhone(){
        return Phone;
    }

    String getEmail(){
        return Email;
    }

    String getAddress(){
        return Address;
    }

    int getId(){
        return Id;
    }




}
