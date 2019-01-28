package com.virtualskillset.discountmania.merchant.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCat {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cat")
    @Expose
    private String cat;
    @SerializedName("subCat")
    @Expose
    private String subCat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

}
