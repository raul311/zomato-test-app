package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.RestaurantsFragment;

/**
 * @raul311
 */

public class RestaurantsActivity extends Activity {

    private FragmentManager fragmentManager;
    private double latitud;
    private double longitud;
    private String categories;
    private String cuisines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        fragmentManager = getFragmentManager();

        Intent intent = getIntent();
        this.latitud = intent.getDoubleExtra("latitud", 0);
        this.longitud = intent.getDoubleExtra("longitud", 0);
        this.cuisines = intent.getStringExtra("cuisines");
        this.categories = intent.getStringExtra("categories");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurants, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RestaurantsFragment restaurantsFragment = RestaurantsFragment.getInstance(latitud,
                longitud, categories, cuisines);
        fragmentManager.beginTransaction().add(R.id.fragment_restaurant_container,
                restaurantsFragment).commit();
    }
}
