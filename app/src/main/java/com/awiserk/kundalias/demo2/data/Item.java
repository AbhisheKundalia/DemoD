package com.awiserk.kundalias.demo2.data;

import com.google.firebase.database.Exclude;

import java.util.List;

/**
 * Created by Abhishek on 5/27/2017.
 */

public class Item {
    private String category;
    private String name;
    private String imageUrl;
    private long cost;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private long timestamp;
    private List<String> availableSizes;

    public Item() {
    }

    public Item(String category, String name, String imageUrl, long cost, List<String> availableSizes) {
        this.category = category;
        this.name = name;
        this.imageUrl = imageUrl;
        this.cost = cost;
        this.availableSizes = availableSizes;
    }

    @Exclude
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public List<String> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<String> availableSizes) {
        this.availableSizes = availableSizes;
    }
}
