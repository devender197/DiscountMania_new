package com.virtualskillset.discountmania.merchant.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMerOffer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mid")
    @Expose
    private Integer mid;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("expire")
    @Expose
    private String expire;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("disc")
    @Expose
    private String disc;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

}