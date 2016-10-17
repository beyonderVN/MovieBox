package com.longngo.moviebox.ui.adapter.viewholder;

import android.animation.ValueAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.longngo.moviebox.R;

import com.longngo.moviebox.ui.activity.Navigator;

import com.ngohoang.along.appcore.common.DynamicHeightImageView;
import com.ngohoang.along.appcore.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.ngohoang.along.appcore.presentation.viewmodel.TrailerMovieVM;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 10/12/2016.
 *
 */
public class MovieTrailerItemHolder extends BaseViewHolder<TrailerMovieVM> {


    @BindView(R.id.ivBackground)
    DynamicHeightImageView imageView;
    @BindView(R.id.tvTitle)
    TextView des;
    @BindView(R.id.srbStar)
    SimpleRatingBar srbStart;
    TrailerMovieVM movieVM;
    public MovieTrailerItemHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(TrailerMovieVM item) {
        movieVM = item;
        des.setText(movieVM.getMovie().getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView.setRatio(0.6);

        Picasso.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w342"+movieVM.getMovie().getBackdropPath())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable(movieVM.getMovie().getId()))
                .into(imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.navigateToYTPlayActivity(v.getContext(),movieVM.getMovie().getId());



            }
        });
        SimpleRatingBar.AnimationBuilder builder = srbStart.getAnimationBuilder()
                .setRepeatCount(0)
                .setRepeatMode(ValueAnimator.INFINITE)
                .setInterpolator(new BounceInterpolator())

                .setRatingTarget(movieVM.getMovie().getVoteAverage())

                ;
        builder.start();
        if(item.isFullSpan()){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

    }
}
