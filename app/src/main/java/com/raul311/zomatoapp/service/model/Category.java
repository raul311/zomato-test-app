package com.raul311.zomatoapp.service.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author raul311
 */

public class Category {

    @SerializedName("categories")
    @Expose
    private CategoryDetails categories;

    public CategoryDetails getCategories() {
        return categories;
    }

    public void setCategories(CategoryDetails categories) {
        this.categories = categories;
    }
}
