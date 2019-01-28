package com.virtualskillset.discountmania.admin.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMerchantList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private Long number;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("paid")
    @Expose
    private int paid;
    @SerializedName("expiry")
    @Expose
    private String expiry;
    @SerializedName("pay_method")
    @Expose
    private String payMethod;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("b_cat")
    @Expose
    private String bCat;
    @SerializedName("b_subCat")
    @Expose
    private String bSubCat;
    @SerializedName("b_name")
    @Expose
    private String bName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("b_desc")
    @Expose
    private String bDesc;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("longi")
    @Expose
    private String longi;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("tin")
    @Expose
    private String tin;
    @SerializedName("cin")
    @Expose
    private String cin;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("whattsapp")
    @Expose
    private String whattsapp;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("insta")
    @Expose
    private String insta;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("d_min")
    @Expose
    private String dMin;
    @SerializedName("d_max")
    @Expose
    private String dMax;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("c_served")
    @Expose
    private Integer cServed;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("approved")
    @Expose
    private int approved;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBCat() {
        return bCat;
    }

    public void setBCat(String bCat) {
        this.bCat = bCat;
    }

    public String getBSubCat() {
        return bSubCat;
    }

    public void setBSubCat(String bSubCat) {
        this.bSubCat = bSubCat;
    }

    public String getBName() {
        return bName;
    }

    public void setBName(String bName) {
        this.bName = bName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBDesc() {
        return bDesc;
    }

    public void setBDesc(String bDesc) {
        this.bDesc = bDesc;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLati() {
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
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getWhattsapp() {
        return whattsapp;
    }

    public void setWhattsapp(String whattsapp) {
        this.whattsapp = whattsapp;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getDMin() {
        return dMin;
    }

    public void setDMin(String dMin) {
        this.dMin = dMin;
    }

    public String getDMax() {
        return dMax;
    }

    public void setDMax(String dMax) {
        this.dMax = dMax;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getCServed() {
        return cServed;
    }

    public void setCServed(Integer cServed) {
        this.cServed = cServed;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

}

