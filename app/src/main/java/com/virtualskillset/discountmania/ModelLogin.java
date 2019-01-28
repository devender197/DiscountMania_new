package com.virtualskillset.discountmania;

import com.google.gson.annotations.SerializedName;

public class ModelLogin {

    @SerializedName("number")
    public String number;
    @SerializedName("password")
    public String password;
    @SerializedName("error_msg")
    public String ResponseMessage;

    @SerializedName("role")
    public String role;

    @SerializedName("id")
    public int id;
    @SerializedName("status")
    public String status;


    @SerializedName("name")
    public String name;



    @SerializedName("error")
    public boolean error;


    public ModelLogin(String number,String password) {
        this.number=number;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public String getPassword() { return password; }
    public String getResponseMessage() { return ResponseMessage; }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }
    public boolean isError() {
        return error;
    }
}
