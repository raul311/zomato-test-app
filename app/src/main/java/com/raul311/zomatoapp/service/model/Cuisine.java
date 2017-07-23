
package com.raul311.zomatoapp.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuisine {

    @SerializedName("cuisine")
    @Expose
    private CuisineDetails cuisine;

    public CuisineDetails getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineDetails cuisine) {
        this.cuisine = cuisine;
    }

}
