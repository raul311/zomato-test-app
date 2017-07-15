package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.MainActivityFragment;

/**
 * @author raul311
 */
public class MainActivity extends Activity implements MainActivityFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().add(R.id.fragment_list_container,
                new MainActivityFragment()).commit();
    }

    @Override
    public void openCategories() {
        Intent categoriesIntent = new Intent(this, CategoriesActivity.class);
        startActivity(categoriesIntent);
    }

    @Override
    public void openRestaurants() {

    }

    @Override
    public void openBookmarks() {

    }

}
