package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.CuisinesFragment;

/**
 * @author raul311
 */

public class CuisineActivity extends Activity implements CuisinesFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private double latitud;
    private double longitud;
    private String categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisines);
        fragmentManager = getFragmentManager();
        Intent intent = getIntent();
        latitud = intent.getDoubleExtra("latitud", 0);
        longitud = intent.getDoubleExtra("longitud", 0);
        categories = intent.getStringExtra("Categories");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cuisines, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().add(R.id.fragment_cuisines_container,
                new CuisinesFragment(latitud, longitud)).commit();
    }

    @Override
    public void openSearch(String cuisines) {
        Log.d("", categories + cuisines);
    }

}
