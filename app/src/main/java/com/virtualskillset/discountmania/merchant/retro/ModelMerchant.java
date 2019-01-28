package com.virtualskillset.discountmania.merchant.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMerchant {
    @SerializedName("name")
    public String name;
    @SerializedName("b_name")
    public String b_name;
    @SerializedName("number")
    public String number;
    @SerializedName("password")
    public String password;
    @SerializedName("street")
    public String street;
    @SerializedName("locality")
    public String locality;
    @SerializedName("pin")
    public String pin;
    @SerializedName("district")
    public String district;
    @SerializedName("state")
    public String state;
    @SerializedName("email")
    public String email;
    @SerializedName("whattsapp")
    public String whattsapp;
    @SerializedName("b_cat")
    public String b_cat;
    @SerializedName("b_subCat")
    public String b_subCat;
    @SerializedName("pan")
    public String pan;
    @SerializedName("tin")
    public String tin;
    @SerializedName("cin")
    public String cin;
    @SerializedName("gst")
    public String gst;
    @SerializedName("b_desc")
    public String b_desc;
    @SerializedName("facebook")
    public String facebook;
    @SerializedName("twitter")
    public String twitter;
    @SerializedName("insta")
    public String insta;
    @SerializedName("youtube")
    public String youtube;
    @SerializedName("day")
    public String day;
    @SerializedName("time")
    public String time;
    @SerializedName("lati")
    public String lati;
    @SerializedName("longi")
    public String longi;
    @SerializedName("pay_method")
    public String pay_method;
    @SerializedName("created_by")
    public String created_by;
    @SerializedName("role")
    public String role;
    @SerializedName("status")
    public String status;

    @SerializedName("ResponseCode")
    public String ResponseCode;
    @SerializedName("error_msg")
    public String ResponseMessage;

    //@SerializedName("subCat")
    //public List<ModelCatArray> subCatResults = null;;

    public ModelMerchant(String name, String b_name, String number, String password, String street, String locality, String pin,
                         String district, String state, String email, String whattsapp, String b_cat, String b_subCat,
                         String pan, String tin, String cin, String gst, String b_desc, String facebook, String twitter, String insta,
                         String youtube, String day, String time, String lati, String longi, String pay_method, String created_by, String role, String status) {
        this.name = name;
        this.b_name = b_name;
        this.number=number;
        this.password=password;
        this.street=street;
        this.locality=locality;
        this.pin=pin;
        this.district=district;
        this.state=state;
        this.email=email;
        this.whattsapp=whattsapp;
        this.b_cat=b_cat;
        this.b_subCat=b_subCat;
        this.pan=pan;
        this.tin=tin;
        this.cin=cin;
        this.gst=gst;
        this.b_desc=b_desc;
        this.facebook=facebook;
        this.twitter=twitter;
        this.insta=insta;
        this.youtube=youtube;
        this.day=day;
        this.time=time;
        this.lati=lati;
        this.longi=longi;
        this.pay_method=pay_method;
        this.created_by=created_by;
        this.role=role;
        this.status=status;
    }

    public ModelMerchant(String name, String b_name, String number, String password, String email, String b_cat, String b_subCat, String created_by) {
        this.name = name;
        this.b_name = b_name;
        this.number=number;
        this.password=password;
        this.email=email;
        this.b_cat=b_cat;
        this.b_subCat=b_subCat;
        this.created_by=created_by;
    }

    public ModelMerchant(String b_cat) {
        this.b_cat=b_cat;
    }

   /* public List<ModelCatArray> getSubCatResults() {
        return subCatResults;
    }*/
    public String getName() {
        return name;
    }

    public String getB_name() {
        return b_name;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public String getStreet() {
        return street;
    }

    public String getLocality() {
        return locality;
    }

    public String getPin() {
        return pin;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public String getWhattsapp() {
        return whattsapp;
    }

    public String getB_cat() {
        return b_cat;
    }

    public String getB_subCat() {
        return b_subCat;
    }

    public String getPan() {
        return pan;
    }

    public String getTin() {
        return tin;
    }

    public String getCin() {
        return cin;
    }

    public String getGst() {
        return gst;
    }

    public String getB_desc() {
        return b_desc;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInsta() {
        return insta;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getLati() {
        return lati;
    }

    public String getLongi() {
        return longi;
    }

    public String getPay_method() {
        return pay_method;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

}

