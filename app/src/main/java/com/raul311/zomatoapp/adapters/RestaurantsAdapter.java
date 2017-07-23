package com.raul311.zomatoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Joiner;
import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.service.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raul311
 */

public class RestaurantsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //private static final int TYPE_BUTTON = 2;

    private final RestaurantsAdapterListener restaurantsAdapterListener;
    private List<Restaurant> restaurantList;
    private List<String> selectedCuisines = new ArrayList<>();
    private Context context;

    public interface RestaurantsAdapterListener {
        void onCuisineSelected(String cuisines);
    }

    public RestaurantsAdapter(Context context, List<Restaurant> restaurantList, RestaurantsAdapterListener restaurantsAdapterListener) {
        this.context = context;
        this.restaurantsAdapterListener = restaurantsAdapterListener;
        this.restaurantList = restaurantList;
        this.restaurantList.add(0, null);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

        if (getItemViewType(position) == TYPE_ITEM) {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;
            final Restaurant restaurant = restaurantList.get(position);
            if (restaurantList != null) {
                holder.name.setText(restaurant.getRestaurant().getName());
                holder.address.setText(restaurant.getRestaurant().getLocation().getAddress());
                holder.locality.setText(restaurant.getRestaurant().getLocation().getLocality());

            }
        } /*else if (getItemViewType(position) == TYPE_BUTTON) {
            final ButtonViewHolder holder = (ButtonViewHolder) viewHolder;
            holder.continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateAndContinue();
                }
            });
        }
        */
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_list_layout, parent, false);
            return new ItemViewHolder(layoutView);
        } /*else if (viewType == TYPE_BUTTON) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_button_layout, parent, false);
            return new ButtonViewHolder(layoutView);
        }*/
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        /*
        if (isPositionButton(position)) {
            return TYPE_BUTTON;
        }
        */
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
/*
    private boolean isPositionButton(int position) {
        return position >= restaurantList.size();
    }
*/
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView address;
        public TextView locality;

        public ItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.restaurant_name);
            address = (TextView) view.findViewById(R.id.restaurant_address);
            locality = (TextView) view.findViewById(R.id.restaurant_locality);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.restaurant_header);
        }
    }
/*
    public class ButtonViewHolder extends RecyclerView.ViewHolder {
        public Button continueButton;

        public ButtonViewHolder(View itemView) {
            super(itemView);
            continueButton = (Button) itemView.findViewById(R.id.cuisine_button);
        }
    }

    private void validateAndContinue() {
        if (!selectedCuisines.isEmpty()) {
            Joiner joiner = Joiner.on(",").skipNulls();
            String cuisines = joiner.join(selectedCuisines);
            Log.d("", cuisines);
            restaurantsAdapterListener.onCuisineSelected(cuisines);
        } else {
            Toast.makeText(context, "No categories are selected", Toast.LENGTH_LONG).show();
        }
    }
*/
}
