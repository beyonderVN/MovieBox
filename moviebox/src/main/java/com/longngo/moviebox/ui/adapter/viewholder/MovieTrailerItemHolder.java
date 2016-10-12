package com.longngo.moviebox.ui.adapter.viewholder;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longngo.moviebox.R;
import com.longngo.moviebox.common.DynamicHeightImageView;
import com.longngo.moviebox.common.recyclerviewhelper.PlaceHolderDrawableHelper;
import com.longngo.moviebox.ui.viewmodel.TrailerMovieVM;

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

        Glide.with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/w342"+item.getMovie().getBackdropPath())
                .asBitmap()
                .placeholder(PlaceHolderDrawableHelper.getBackgroundDrawable())
                .into(imageView);

        if(item.isFullSpan()){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
        }

    }
}
