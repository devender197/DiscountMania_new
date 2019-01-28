package com.virtualskillset.discountmania.merchant.retro;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMerDash {

    @SerializedName("served")
    @Expose
    private Integer served;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;

    public Integer getServed() {
        return served;
    }

    public void setServed(Integer served) {
        this.served = served;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    public class Review {

        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}