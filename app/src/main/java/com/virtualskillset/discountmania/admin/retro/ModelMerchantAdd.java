package com.virtualskillset.discountmania.admin.retro;

import com.google.gson.annotations.SerializedName;

public class ModelMerchantAdd {
    private String success;

    public ModelMerchantAdd(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getMid() {
        return mid;
    }

    @SerializedName("id")
    public String mid;
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
    @SerializedName("created_by")
    public String created_by;
    @SerializedName("d_min")
    public String dis_min;
    @SerializedName("d_max")
    public String dis_max;

    @SerializedName("error")
    public boolean ResponseCode;
    @SerializedName("error_msg")
    public String ResponseMessage;

    @SerializedName("_method")
    public String methodupd;



    public ModelMerchantAdd(String name,String b_name,String number,String password,String street,String locality,String pin,
                            String district,String state,String email,String whattsapp,String b_cat,String b_subCat,
                            String pan,String tin,String cin,String gst,String b_desc,String facebook,String twitter,String insta,
                            String youtube,String day,String time,String lati,String longi,String created_by,String dis_min,String dis_max) {
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
        this.created_by=created_by;
        this.dis_min=dis_min;
        this.dis_max=dis_max;
    }
    public ModelMerchantAdd(String name,String b_name,String street,String locality,String pin,
                            String district,String state,String email,String whattsapp,String b_cat,String b_subCat,
                            String pan,String tin,String cin,String gst,String b_desc,String facebook,String twitter,String insta,
                            String youtube,String day,String time,String lati,String longi/*,String created_by*/,String dis_min,String dis_max,String methodupd) {
        this.name = name;
        this.b_name = b_name;
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
        //this.created_by=created_by;
        this.dis_min=dis_min;
        this.dis_max=dis_max;
        this.methodupd=methodupd;
    }

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


    public String getCreated_by() {
        return created_by;
    }

    public String getDis_min() {
        return dis_min;
    }

    public String getDis_max() {
        return dis_max;
    }

    public boolean getResponseCode() {
        return ResponseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }

}
