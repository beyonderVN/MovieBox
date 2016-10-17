package com.longngo.moviebox.wear.adapter.holder;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;

import butterknife.ButterKnife;

/**
 * Created by Long on 10/5/2016.
 *
 */

public class NoMoreItemHolder extends BaseViewHolder<MovieVM> {
    private static final String TAG = "NoMoreItemHolder";

    public NoMoreItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public  void bind(MovieVM item) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
        layoutParams.setFullSpan(true);
    }
}
