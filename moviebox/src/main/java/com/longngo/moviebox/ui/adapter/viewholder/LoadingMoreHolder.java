package com.longngo.moviebox.ui.adapter.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.support.v7.widget.CardView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.ui.activity.Navigator;
import com.longngo.moviebox.ui.viewmodel.LoadingMoreVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.longngo.moviebox.R.id.wrap;

/**
 * Created by Long on 10/5/2016.
 *
 */

public class LoadingMoreHolder extends BaseViewHolder<LoadingMoreVM> {
    private static final String TAG = "MovieViewHolder";
    ProgressBar progress;
    public LoadingMoreHolder(View itemView) {
        super(itemView);
        progress = (ProgressBar) itemView;
    }

    @Override
    public  void bind(LoadingMoreVM item) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
        layoutParams.setFullSpan(true);
        progress.setVisibility(View.VISIBLE );

    }
}
