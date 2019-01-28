package com.virtualskillset.discountmania.user.model;

import android.support.annotation.NonNull;

public class ModelUsrMerLst  {
    private int id;
    private String number;
    private String bCat;
    private String bSubCat;
    private String bName;
    private String locality;
    private String img;
    private String street;
    private String pin;
    private String district;
    private String state;
    private String bDesc;
    /*private String lati;
    private String longi;*/
    private double distt;

    public ModelUsrMerLst(int id,String number,String bCat,String bSubCat,String bName,String locality,String img,String street,String pin,String district,String state,String bDesc,/*String lati,String longi*/double distt)
    {
        this.id=id;
        this.number=number;
        this.bCat=bCat;
        this.bSubCat=bSubCat;
        this.bName=bName;
        this.locality=locality;
        this.img=img;
        this.street=street;
        this.pin=pin;
        this.district=district;
        this.state=state;
        this.bDesc=bDesc;
       /* this.lati=lati;
        this.longi=longi;*/
        this.distt=distt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getbCat() {
        return bCat;
    }

    public void setbCat(String bCat) {
        this.bCat = bCat;
    }

    public String getbSubCat() {
        return bSubCat;
    }

    public void setbSubCat(String bSubCat) {
        this.bSubCat = bSubCat;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getbDesc() {
        return bDesc;
    }

    public void setbDesc(String bDesc) {
        this.bDesc = bDesc;
    }

    /*public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }*/
    public double getDistt() {
        return distt;
    }

    public void setbDesc(double distt) {
        this.distt = distt;
    }

}
