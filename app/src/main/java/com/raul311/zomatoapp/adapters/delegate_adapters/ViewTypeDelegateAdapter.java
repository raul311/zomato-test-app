package com.raul311.zomatoapp.adapters.delegate_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @raul311
 */

public interface ViewTypeDelegateAdapter<VH extends RecyclerView.ViewHolder, T extends ViewType> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH holder, T item);
}
