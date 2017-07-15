package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.CategoriesFragment;

/**
 * @author raul311
 */

public class CategoriesActivity extends Activity {

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

}
