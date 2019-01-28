package com.virtualskillset.discountmania.admin.retro;


import com.google.gson.annotations.SerializedName;

public class Model {
    private String success;

    public Model(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    //////////pc/////////////

    @SerializedName("cat")
    public String cate;
    @SerializedName("subCat")
    public String subCate;

    @SerializedName("ResponseCode")
    public String ResponseCode;
    @SerializedName("error_msg")
    public String ResponseMessage;

    public Model(String cate, String subCate) {
        this.cate = cate;
        this.subCate = subCate;
    }

    public String getCate() {
        return cate;
    }

    public String getSubCate() {
        return subCate;
    }

    public String getResponseCode() {
        return ResponseCode;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }
}
