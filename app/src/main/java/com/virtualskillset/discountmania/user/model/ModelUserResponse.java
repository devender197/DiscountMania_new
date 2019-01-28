package com.virtualskillset.discountmania.user.model;

import com.google.gson.annotations.SerializedName;

public class ModelUserResponse {
    public String getResponseMessage() {
        return ResponseMessage;
    }

    @SerializedName("error_msg")
    public String ResponseMessage;
}
