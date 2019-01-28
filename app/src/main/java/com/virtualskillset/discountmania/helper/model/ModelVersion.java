package com.virtualskillset.discountmania.helper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelVersion {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("vers")
    @Expose
    private int vers;

    @SerializedName("comp")
    @Expose
    private int comp;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVers() {
        return vers;
    }

    public void setVers(int vers) {
        this.vers = vers;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }


}