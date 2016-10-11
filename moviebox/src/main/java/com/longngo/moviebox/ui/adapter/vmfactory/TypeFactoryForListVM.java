package com.longngo.moviebox.ui.adapter.vmfactory;

import android.view.View;

import com.longngo.moviebox.R;
import com.longngo.moviebox.ui.adapter.viewholder.BaseViewHolder;
import com.longngo.moviebox.ui.adapter.viewholder.MovieViewHolder;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

/**
 * Created by Long on 10/5/2016.
 */

public class TypeFactoryForListVM implements VMTypeFactory {

    @Override
    public int getType(MovieVM movieVM) {
        return R.layout.layout_item_movie;
    }



    @Override
    public BaseViewHolder createHolder(int type, View view) {
        switch(type) {
            case R.layout.layout_item_movie:
                return new MovieViewHolder(view);
        }
        return null;
    }
}
