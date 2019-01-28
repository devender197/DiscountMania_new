package com.virtualskillset.discountmania.user.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUserData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("role")
        @Expose
        private Integer role;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("paid")
        @Expose
        private Integer paid;
        @SerializedName("expiry")
        @Expose
        private String expiry;
        @SerializedName("pay_method")
        @Expose
        private String payMethod;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("father")
        @Expose
        private String father;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("profession")
        @Expose
        private String profession;
        @SerializedName("pan")
        @Expose
        private String pan;
        @SerializedName("aadhar")
        @Expose
        private String aadhar;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("long")
        @Expose
        private Double _long;
        @SerializedName("pincode")
        @Expose
        private Integer pincode;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getPaid() {
            return paid;
        }

        public void setPaid(Integer paid) {
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getFather() {
            return father;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getPan() {
            return pan;
        }

        public void setPan(String pan) {
            this.pan = pan;
        }

        public String getAadhar() {
            return aadhar;
        }

        public void setAadhar(String aadhar) {
            this.aadhar = aadhar;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLong() {
            return _long;
        }

        public void setLong(Double _long) {
            this._long = _long;
        }

        public Integer getPincode() {
            return pincode;
        }

        public void setPincode(Integer pincode) {
            this.pincode = pincode;
        }

    }