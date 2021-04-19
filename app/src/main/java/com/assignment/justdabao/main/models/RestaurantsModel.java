package com.assignment.justdabao.main.models;

import java.io.Serializable;

public class RestaurantsModel implements Serializable {
    String id;
    String title;
    String shortDescription;
    String rating;
    double lat;
    double lng;
    String image_url;
    String avg_price;
    String pickUpType;


    @Override
    public String toString() {
        return "RestaurantsModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", rating='" + rating + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", image_url='" + image_url + '\'' +
                ", avg_price='" + avg_price + '\'' +
                ", pickUpType='" + pickUpType + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    public String getPickUpType() {
        return pickUpType;
    }

    public void setPickUpType(String pickUpType) {
        this.pickUpType = pickUpType;
    }
}
