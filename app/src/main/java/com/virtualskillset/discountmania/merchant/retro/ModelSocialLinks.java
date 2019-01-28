package com.virtualskillset.discountmania.merchant.retro;

public class ModelSocialLinks {
    String url,type;

    public ModelSocialLinks(String url, String type)
    {
        this.url=url;
        this.type=type;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
