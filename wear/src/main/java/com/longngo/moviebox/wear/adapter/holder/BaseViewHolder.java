package com.longngo.moviebox.wear.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Long on 10/5/2016.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
