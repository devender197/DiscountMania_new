package com.virtualskillset.discountmania.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District {
    @SerializedName("districtname")
    @Expose
    private String districtname;

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

}
