package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.CategoriesFragment;

/**
 * @author raul311
 */

public class CategoriesActivity extends Activity implements CategoriesFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        fragmentManager = getFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categories, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().add(R.id.fragment_categories_container,
                new CategoriesFragment()).commit();
    }

    @Override
    public void openCuisines(String categories) {
        Log.d("", categories);
        Intent cuisineIntent = new Intent(this, CuisinesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Categories", categories);
        Intent intent = getIntent();
        bundle.putDouble("latitud", intent.getDoubleExtra("latitud", 0));
        bundle.putDouble("longitud", intent.getDoubleExtra("longitud", 0));
        cuisineIntent.putExtras(bundle);
        startActivity(cuisineIntent);
    }

}
