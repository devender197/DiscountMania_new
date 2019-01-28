package com.virtualskillset.discountmania.user.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDistricts {

    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("districts")
    @Expose
    private List<District> districts = null;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }


}