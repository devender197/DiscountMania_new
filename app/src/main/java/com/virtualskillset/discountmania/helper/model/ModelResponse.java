package com.virtualskillset.discountmania.helper.model;

import com.google.gson.annotations.SerializedName;

public class ModelResponse {


    @SerializedName("error")
    public boolean error;

    @SerializedName("error_msg")
    public String ResponseMessage;

    public boolean getError() {
        return error;
    }

    public String getResponseMessage() {
        return ResponseMessage;
    }
}
