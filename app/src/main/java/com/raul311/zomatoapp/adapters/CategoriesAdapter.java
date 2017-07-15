package com.raul311.zomatoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.raul311.zomatoapp.R;
import com.raul311.zomatoapp.service.model.Category;

import java.util.List;

/**
 * @author raul311
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final CategoriesAdapterListener categoriesAdapterListener;
    private List<Category> categoryList;

    public interface CategoriesAdapterListener {
        void onCategorySelected(Category category);
    }

    public CategoriesAdapter(Context context, List<Category> categoryList, CategoriesAdapterListener categoriesAdapterListener) {
        this.categoriesAdapterListener = categoriesAdapterListener;
        this.categoryList = categoryList;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        if (category != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoriesAdapterListener.onCategorySelected(category);
                }
            });
        } else {
            holder.itemView.setOnClickListener(null);
        }
        holder.name.setText(category.getCategories().getName());
    }

    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.categoryName);
        }
    }

}
