package com.longngo.moviebox.wear.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.longngo.moviebox.wear.R;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieDetailVM;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Long on 10/5/2016.
 *
 */

public class MovieDetailHolder extends BaseViewHolder<MovieDetailVM> {
    private static final String TAG = "MovieViewHolder";
    @BindView(R.id.image_shot)
    ImageView imageView;


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
        MovieDetailVM movieVM = item;

        Picasso.with(imageView.getContext())
                .load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getPosterPath())
                .into(imageView);


    }
}
