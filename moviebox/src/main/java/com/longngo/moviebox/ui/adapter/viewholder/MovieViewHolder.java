package com.longngo.moviebox.ui.adapter.viewholder;

import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.longngo.moviebox.ui.activity.Navigator;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.longngo.moviebox.R.id.wrap;

/**
 * Created by Long on 10/5/2016.
 *
 */

public class MovieViewHolder extends BaseViewHolder<MovieVM> {
    private static final String TAG = "MovieViewHolder";
    @BindView(wrap)
    CardView cardView;
    @BindView(R.id.ivBackground)
    DynamicHeightImageView imageView;
    @BindView(R.id.tvTitle)
    TextView des;
    MovieVM movieVM;
    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public  void bind(MovieVM item) {
        movieVM = item;
        des.setText(movieVM.getMovie().getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View sharedView = imageView;
                ActivityOptions ops = ActivityOptions.makeSceneTransitionAnimation((Activity) itemView.getContext(),
                        sharedView,
                        itemView.getContext().getString(R.string.detail_image));
                Navigator.navigateToDetailActivity(v.getContext(), movieVM,ops);
            }
        });
//        imageView.setRatio(1.5);
        Glide.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w300_and_h300_bestv2"+item.getMovie().getBackdropPath())
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable())
                .into(imageView);

        if(item.getMovie().getVoteAverage()>5){
//            Log.d(TAG, "bind: " +item.getMovie().getVoteAverage());
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }else {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
        }
//        ImageView imageView = (ImageView) itemView.findViewById(R.id.anima);
//        imageView.setBackgroundResource(R.drawable.anim_android);
//        AnimationDrawable progressAnimation = (AnimationDrawable) imageView.getBackground();
//        progressAnimation.start();
    }
}
