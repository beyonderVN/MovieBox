package com.longngo.moviebox.wear.adapter;

import android.view.View;


import com.longngo.moviebox.wear.R;
import com.longngo.moviebox.wear.adapter.holder.BaseViewHolder;
import com.longngo.moviebox.wear.adapter.holder.LoadingMoreHolder;
import com.longngo.moviebox.wear.adapter.holder.MovieDetailHolder;
import com.longngo.moviebox.wear.adapter.holder.MovieTrailerItemHolder;
import com.longngo.moviebox.wear.adapter.holder.MovieViewHolder;
import com.longngo.moviebox.wear.adapter.holder.NoMoreItemHolder;
import com.ngohoang.along.appcore.presentation.viewmodel.LoadingMoreVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieDetailVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;
import com.ngohoang.along.appcore.presentation.viewmodel.NoMoreItemVM;
import com.ngohoang.along.appcore.presentation.viewmodel.TrailerMovieVM;

/**
 * Created by Long on 10/5/2016.
 */

public class HolderFactoryImpl implements HolderFactory {
    private static final int ITEM_MOVIE = R.layout.layout_item_movie;
    private static final int LOADING_MORE = R.layout.infinite_loading;
    private static final int NO_MORE = R.layout.infinite_no_more;
    private static final int MOVIE_DETAIL = R.layout.layout_movie_detail;
    private static final int MOVIE_TRAILER_ITEM = R.layout.layout_movie_trailer_item;
    @Override
    public int getType(MovieVM movieVM) {
        return ITEM_MOVIE;
    }



    @Override
    public BaseViewHolder createHolder(int type, View view) {
        switch(type) {
            case ITEM_MOVIE:
                return new MovieViewHolder(view);
            case LOADING_MORE:
                return new LoadingMoreHolder(view);
            case NO_MORE:
                return new NoMoreItemHolder(view);
            case MOVIE_DETAIL:
                return new MovieDetailHolder(view);
            case MOVIE_TRAILER_ITEM:
                return new MovieTrailerItemHolder(view);
        }
        return null;
    }

    @Override
    public int getType(LoadingMoreVM loadingMoreVM) {
        return LOADING_MORE;
    }

    @Override
    public int getType(NoMoreItemVM noMoreItemVM) {
        return NO_MORE;
    }

    @Override
    public int getType(MovieDetailVM movieDetailVM) {
        return MOVIE_DETAIL;
    }

    @Override
    public int getType(TrailerMovieVM trailerMovieVM) {
        return MOVIE_TRAILER_ITEM;
    }
}
