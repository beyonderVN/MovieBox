package com.longngo.moviebox.wear.adapter;

import android.view.View;


import com.longngo.moviebox.wear.adapter.holder.BaseViewHolder;
import com.ngohoang.along.appcore.presentation.viewmodel.VMTypeFactory;


/**
 * Created by Long on 10/5/2016.
 */

public interface HolderFactory extends VMTypeFactory {
    BaseViewHolder createHolder(int type, View view);
}
