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
import com.raul311.zomatoapp.adapters.CategoriesAdapter;
import com.raul311.zomatoapp.adapters.CuisinesAdapter;
import com.raul311.zomatoapp.constants.Constants;
import com.raul311.zomatoapp.service.ZomatoServiceManager;
import com.raul311.zomatoapp.service.business.EndpointInterface;
import com.raul311.zomatoapp.service.business.model.CategoriesResponse;
import com.raul311.zomatoapp.service.business.model.CuisinesResponse;
import com.raul311.zomatoapp.service.model.Category;
import com.raul311.zomatoapp.service.model.Cuisine;

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

public class CuisinesFragment extends Fragment implements CuisinesAdapter.CuisinesAdapterListener {

    public ZomatoServiceManager zomatoServiceManager;
    public Retrofit retrofit;
    public EndpointInterface apiService;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private OnFragmentInteractionListener actionListener;
    final private double latitud;
    final private double longitud;

    public interface OnFragmentInteractionListener {
        void openSearch(String cuisines);
    }

    public CuisinesFragment(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(EndpointInterface.class);

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

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        callRetrofit();
    }

    private void callRetrofit() {

        Call<CuisinesResponse> call = apiService.getCuisines(latitud, longitud);
        call.enqueue(new Callback<CuisinesResponse>() {
            @Override
            public void onResponse(Call<CuisinesResponse> call, Response<CuisinesResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                int statusCode = response.code();
                CuisinesResponse cuisinesResponse = response.body();
                updateAdapter(cuisinesResponse.getCuisines());
            }

            @Override
            public void onFailure(Call<CuisinesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("main", "onFailure: ");
            }
        });

    }

    private void updateAdapter(List<Cuisine> cuisineList) {
        adapter = new CuisinesAdapter(getActivity(), cuisineList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCuisineSelected(String cuisines) {
        Log.d("main", "onCategorySelected ");
        actionListener.openSearch(cuisines);
    }

}
