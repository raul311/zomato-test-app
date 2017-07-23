
package com.raul311.zomatoapp.service.business.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raul311.zomatoapp.service.model.Cuisine;

public class CuisinesResponse {

    @SerializedName("cuisines")
    @Expose
    private List<Cuisine> cuisines = null;

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

}
