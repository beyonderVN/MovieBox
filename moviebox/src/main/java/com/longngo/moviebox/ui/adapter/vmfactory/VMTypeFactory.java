package com.longngo.moviebox.ui.adapter.vmfactory;

import android.view.View;

import com.longngo.moviebox.ui.adapter.viewholder.BaseViewHolder;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

/**
 * Created by Long on 10/5/2016.
 */

public interface VMTypeFactory {
    int getType(MovieVM movieVM);

    BaseViewHolder createHolder(int type, View view);
}
