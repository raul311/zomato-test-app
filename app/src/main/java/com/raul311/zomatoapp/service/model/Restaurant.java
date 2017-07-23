package com.raul311.zomatoapp.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author raul311
 */
public class Restaurant {

    @SerializedName("restaurant")
    @Expose
    private RestaurantDetails restaurant;

    public RestaurantDetails getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDetails restaurant) {
        this.restaurant = restaurant;
    }

}
