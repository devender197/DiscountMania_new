package com.virtualskillset.discountmania.merchant.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelProducts {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("desc")
    @Expose
    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}