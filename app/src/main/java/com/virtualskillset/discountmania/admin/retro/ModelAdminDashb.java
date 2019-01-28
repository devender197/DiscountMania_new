package com.virtualskillset.discountmania.admin.retro;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelAdminDashb {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("requests")
    @Expose
    private Integer requests;
    @SerializedName("users")
    @Expose
    private Integer users;
    @SerializedName("admins")
    @Expose
    private Integer admins;
    @SerializedName("performers")
    @Expose
    private List<Performer> performers = null;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRequests() {
        return requests;
    }

    public void setRequests(Integer requests) {
        this.requests = requests;
    }

    public Integer getUsers() {
        return users;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public Integer getAdmins() {
        return admins;
    }

    public void setAdmins(Integer admins) {
        this.admins = admins;
    }

    public List<Performer> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Performer> performers) {
        this.performers = performers;
    }


    public class Performer {

        @SerializedName("created_by")
        @Expose
        private String createdBy;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("name")
        @Expose
        private String name;

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}