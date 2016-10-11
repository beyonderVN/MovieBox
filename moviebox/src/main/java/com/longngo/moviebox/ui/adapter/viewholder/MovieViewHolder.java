package com.longngo.moviebox.ui.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.ui.activity.Navigator;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Long on 10/5/2016.
 *
 */

public class MovieViewHolder extends BaseViewHolder<MovieVM> {
    @BindView(R.id.wrap)
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
        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigator.navigateToCompetionDetailActivity(v.getContext(), movieVM.getMovie());
            }
        });
//        imageView.setRatio(1.5);
        Glide.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185_and_h278_bestv2"+item.getMovie().getBackdropPath())
                .asBitmap().into(imageView);
    }
}
