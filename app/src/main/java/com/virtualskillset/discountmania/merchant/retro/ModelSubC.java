package com.virtualskillset.discountmania.merchant.retro;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSubC {
    String cate;

    @SerializedName("subCat")
    @Expose
    private List<SubCat> subCat = null;
    public ModelSubC(String cate) {
        this.cate = cate;
    }

    public List<SubCat> getSubCat() {
        return subCat;
    }

    public void setSubCat(List<SubCat> subCat) {
        this.subCat = subCat;
    }

}
