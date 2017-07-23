package com.raul311.zomatoapp.service.business.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.raul311.zomatoapp.service.model.Category;

/**
 * @author 311
 */
public class CategoriesResponse {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
