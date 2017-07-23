package com.raul311.zomatoapp.adapters;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Joiner;
import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.adapters.delegate_adapters.ViewTypeDelegateAdapter;
import com.raul311.zomatoapp.service.model.Category;
import com.raul311.zomatoapp.service.model.Cuisine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raul311
 */

public class CuisinesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BUTTON = 2;
    protected static final int BASE_DELEGATE_ADAPTER_CAPACITY = 5;

    protected final SparseArrayCompat<ViewTypeDelegateAdapter> delegateAdapters;

    private final CuisinesAdapterListener cuisinesAdapterListener;
    private List<Cuisine> cuisineList;
    private SparseArrayCompat x;
    private List<String> selectedCuisines = new ArrayList<>();
    private Context context;

    public interface CuisinesAdapterListener {
        void onCuisineSelected(String cuisines);
    }

    public CuisinesAdapter(Context context, List<Cuisine> cuisineList, CuisinesAdapterListener cuisinesAdapterListener) {
        this.context = context;
        this.cuisinesAdapterListener = cuisinesAdapterListener;
        this.cuisineList = cuisineList;
        this.cuisineList.add(0, null);
        delegateAdapters = new SparseArrayCompat<>(BASE_DELEGATE_ADAPTER_CAPACITY);
        //delegateAdapters.put(TYPE_HEADER, new HeaderViewHolder());
    }
/*
    public CategoriesAdapter(Context context, List<ItemObject> itemObjects, CategoriesAdapterListener categoriesAdapterListener) {
        this.categoriesAdapterListener = categoriesAdapterListener;
        this.itemObjects = itemObjects;
    }
*/
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;
            final Cuisine cuisine = cuisineList.get(position);
            if (cuisine != null) {
                final String cuisineName = cuisine.getCuisine().getCuisineName();
                holder.name.setText(cuisineName);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                        holder.checkBox.setChecked(select);
                        if (select) {
                            selectedCuisines.add(cuisineName);
                        } else {
                            selectedCuisines.remove(cuisineName);
                        }
                    }
                });
            }
        } else if (getItemViewType(position) == TYPE_BUTTON) {
            final ButtonViewHolder holder = (ButtonViewHolder) viewHolder;
            holder.continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateAndContinue();
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {

        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_header_layout, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_list_layout, parent, false);
            return new ItemViewHolder(layoutView);
        } else if (viewType == TYPE_BUTTON) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_button_layout, parent, false);
            return new ButtonViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public int getItemCount() {
        return cuisineList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        if (isPositionButton(position)) {
            return TYPE_BUTTON;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionButton(int position) {
        return position >= cuisineList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CheckBox checkBox;

        public ItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.cuisine_name);
            checkBox = (CheckBox) itemView.findViewById(R.id.cuisine_id);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView headerTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerTitle = (TextView) itemView.findViewById(R.id.cuisine_header);
        }
    }

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
            cuisinesAdapterListener.onCuisineSelected(cuisines);
        } else {
            Toast.makeText(context, "No categories are selected", Toast.LENGTH_LONG).show();
        }
    }

}
