package com.virtualskillset.discountmania.subadmin.helperSubAdmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSubAdmin {

    @SerializedName("merchants")
    @Expose
    private Integer merchants;
    @SerializedName("admins")
    @Expose
    private Integer admins;
    @SerializedName("users")
    @Expose
    private Integer users;
    @SerializedName("myMerchants")
    @Expose
    private Integer myMerchants;

    public Integer getMerchants() {
        return merchants;
    }

    public void setMerchants(Integer merchants) {
        this.merchants = merchants;
    }

    public Integer getAdmins() {
        return admins;
    }

    public void setAdmins(Integer admins) {
        this.admins = admins;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getMyMerchants() {
        return myMerchants;
    }

    public void setMyMerchants(Integer myMerchants) {
        this.myMerchants = myMerchants;
    }

}
