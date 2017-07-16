package com.raul311.zomatoapp.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.fragments.MainActivityFragment;

/**
 * @author raul311
 */
public class MainActivity extends Activity implements MainActivityFragment.OnFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double latitud = 0;
    private double longitud = 0;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            checkPermission();
        }
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                setLocation(location);
                            }
                        }
                    });
        } catch (SecurityException e) {

        }
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
        Log.d("", longitud + " ");
        Log.d("", latitud + " ");
        startActivity(categoriesIntent);
    }

    @Override
    public void openRestaurants() {

    }

    @Override
    public void openBookmarks() {

    }

    public void setLocation(Location location) {
        this.location = location;
        longitud = location.getLongitude();
        latitud = location.getLatitude();
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

}
