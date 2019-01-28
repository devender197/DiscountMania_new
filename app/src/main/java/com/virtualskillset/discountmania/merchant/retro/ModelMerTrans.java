package com.virtualskillset.discountmania.merchant.retro;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMerTrans {

    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("error")
    @Expose
    private Boolean error;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public class Response {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("mid")
        @Expose
        private String mid;
        @SerializedName("mrp")
        @Expose
        private String mrp;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("name")
        @Expose
        private String Name;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getName() {
            return Name;
        }

        public void setBName(String bName) {
            this.Name = Name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}
