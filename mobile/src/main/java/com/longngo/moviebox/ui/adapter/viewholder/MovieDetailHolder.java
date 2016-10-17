package com.longngo.moviebox.ui.adapter.viewholder;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.R;


import com.ngohoang.along.appcore.common.DynamicHeightImageView;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieDetailVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 10/5/2016.
 *
 */

public class MovieDetailHolder extends BaseViewHolder<MovieDetailVM> {
    private static final String TAG = "MovieViewHolder";
    @BindView(R.id.ivHeader)
    DynamicHeightImageView imageView;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    MovieDetailVM movieVM;
    public MovieDetailHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    public MovieDetailHolder(View itemView,boolean fullspan) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public  void bind(MovieDetailVM item) {
        movieVM = item;
        imageView.setRatio(1.5);
        Glide.with(imageView.getContext())
                .load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
                .asBitmap()
                .into(imageView);
        tvOverview.setText(movieVM.getMovie().getOverview());
        tvTitle.setText(movieVM.getMovie().getTitle());
        tvReleaseDate.setText(Html.fromHtml("<b>Release Date: </b><small>"+movieVM.getMovie().getReleaseDate()+"<small>"));
        if(item.isFullSpan()){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

    }
}
