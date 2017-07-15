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

import com.raul311.zomatoapp.constants.Constants;
import com.raul311.zomatoapp.adapters.CategoriesAdapter;
import com.raul311.zomatoapp.service.ZomatoServiceManager;
import com.raul311.zomatoapp.service.business.EndpointInterface;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.service.business.model.CategoriesResponse;
import com.raul311.zomatoapp.service.model.Category;

import java.util.List;

/**
 * @author raul311
 */

public class CategoriesFragment extends Fragment implements CategoriesAdapter.CategoriesAdapterListener {

    public ZomatoServiceManager zomatoServiceManager;
    public Retrofit retrofit;
    public EndpointInterface apiService;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;

    public CategoriesFragment() {

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
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        callRetrofit();
    }

    private void callRetrofit() {

        Call<CategoriesResponse> call = apiService.getCategories();
        call.enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                int statusCode = response.code();
                CategoriesResponse categoriesResponse = response.body();
                updateAdapter(categoriesResponse.getCategories());
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d("main", "onFailure: ");
            }
        });

    }

    private void updateAdapter(List<Category> categoryList) {
        adapter = new CategoriesAdapter(getActivity(), categoryList, this);
        recyclerView.setAdapter(adapter);
    }
/*
    public void onCategorySelected(Category category) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(FragmentConstants.ADVERTISEMENT_SENT, ad);
        startActivity(intent);
    }
  */

    @Override
    public void onCategorySelected(Category category) {
        Log.d("main", "onCategorySelected ");
    }

}
