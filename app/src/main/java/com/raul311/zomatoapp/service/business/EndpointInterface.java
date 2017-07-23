package com.raul311.zomatoapp.service.business;

import com.raul311.zomatoapp.service.business.model.CategoriesResponse;
import com.raul311.zomatoapp.service.business.model.CuisinesResponse;
import com.raul311.zomatoapp.service.business.model.RestaurantsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @author raul311
 */

public interface EndpointInterface {

    @Headers({"Accept: application/json", "user-key: a6e3d486e64d6d9eb7f566ad5eb30191"})
    @GET("api/v2.1/categories")
    Call<CategoriesResponse> getCategories();

    @Headers({"Accept: application/json", "user-key: a6e3d486e64d6d9eb7f566ad5eb30191"})
    @GET("api/v2.1/cuisines")
    Call<CuisinesResponse> getCuisines(
            @Query("lat") double latitud,
            @Query("lon") double longitud);

    @Headers({"Accept: application/json", "user-key: a6e3d486e64d6d9eb7f566ad5eb30191"})
    @GET("api/v2.1/search")
    Call<RestaurantsResponse> getRestaurants(
            @Query("cuisines") String cuinines,
            @Query("category") String category,
            @Query("lat") double latitud,
            @Query("lon") double longitud);

}
