package com.raul311.zomatoapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.service.business.EndpointInterface;

/**
 * @author raul311
 */
public class MainActivityFragment extends Fragment {

    private OnFragmentInteractionListener actionListener;
    public EndpointInterface apiService;

    public interface OnFragmentInteractionListener {
        void openCategories();
        void openRestaurants();
        void openBookmarks();
    }

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        CardView categories = (CardView) view.findViewById(R.id.categories_view);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.openCategories();
            }
        });

        CardView restaurants = (CardView) view.findViewById(R.id.restaurant_view);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.openRestaurants();
            }
        });

        CardView bookmark = (CardView) view.findViewById(R.id.bookmark_view);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.openBookmarks();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
