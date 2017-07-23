package com.raul311.zomatoapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.adapters.RestaurantsAdapter;
import com.raul311.zomatoapp.constants.Constants;
import com.raul311.zomatoapp.service.ZomatoServiceManager;
import com.raul311.zomatoapp.service.business.EndpointInterface;
import com.raul311.zomatoapp.service.business.model.RestaurantsResponse;
import com.raul311.zomatoapp.service.model.Restaurant;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author raul311
 */

public class RestaurantsFragment extends Fragment implements RestaurantsAdapter.RestaurantsAdapterListener {

    public ZomatoServiceManager zomatoServiceManager;
    public Retrofit retrofit;
    public EndpointInterface apiService;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private OnFragmentInteractionListener actionListener;
    private double latitud;
    private double longitud;
    private String categories;
    private String cuisines;

    public interface OnFragmentInteractionListener {
        void openSearch(String cuisines);
    }

    public static RestaurantsFragment getInstance(double latitud, double longitud, String categories,
                                                  String cuisines) {
        RestaurantsFragment restaurantsFragment = new RestaurantsFragment();

        Bundle args = new Bundle();
        args.putDouble("latitud", latitud);
        args.putDouble("longitud", longitud);
        args.putString("categories", categories);
        args.putString("cuisines", cuisines);
        restaurantsFragment.setArguments(args);

        return restaurantsFragment;
    }

    public RestaurantsFragment() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(EndpointInterface.class);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.latitud = args.getDouble("latitud");
        this.longitud = args.getDouble("longitud");
        this.categories = args.getString("categories");
        this.cuisines = args.getString("cuisines");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.scrollable_main);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        return view;
    }
/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            actionListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener.");
        }
    }
*/
    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        callRetrofit();
    }

    private void callRetrofit() {

        Call<RestaurantsResponse> call = apiService.getRestaurants(cuisines, categories,
                latitud, longitud);
        call.enqueue(new Callback<RestaurantsResponse>() {
            @Override
            public void onResponse(Call<RestaurantsResponse> call, Response<RestaurantsResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                int statusCode = response.code();
                RestaurantsResponse restaurantsResponse = response.body();
                updateAdapter(restaurantsResponse.getRestaurants());
            }

            @Override
            public void onFailure(Call<RestaurantsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("main", "onFailure: ");
            }
        });

    }

    private void updateAdapter(List<Restaurant> restaurantList) {
        adapter = new RestaurantsAdapter(getActivity(), restaurantList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCuisineSelected(String cuisines) {
        Log.d("main", "onCategorySelected ");
        actionListener.openSearch(cuisines);
    }

}
