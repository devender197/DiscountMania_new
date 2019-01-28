package com.virtualskillset.discountmania.merchant.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMerCusVerify {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("error")
    @Expose
    private Boolean error;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

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

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
