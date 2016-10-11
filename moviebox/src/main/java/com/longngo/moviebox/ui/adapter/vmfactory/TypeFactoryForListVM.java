package com.longngo.moviebox.ui.adapter.vmfactory;

import android.view.View;

import com.longngo.moviebox.R;
import com.longngo.moviebox.ui.adapter.viewholder.BaseViewHolder;
import com.longngo.moviebox.ui.adapter.viewholder.LoadingMoreHolder;
import com.longngo.moviebox.ui.adapter.viewholder.MovieDetailHolder;
import com.longngo.moviebox.ui.adapter.viewholder.MovieViewHolder;
import com.longngo.moviebox.ui.adapter.viewholder.NoMoreItemHolder;
import com.longngo.moviebox.ui.viewmodel.LoadingMoreVM;
import com.longngo.moviebox.ui.viewmodel.MovieDetailVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;
import com.longngo.moviebox.ui.viewmodel.NoMoreItemVM;

/**
 * Created by Long on 10/5/2016.
 */

public class TypeFactoryForListVM implements VMTypeFactory {
    private static final int ITEM_MOVIE = R.layout.layout_item_movie;
    private static final int LOADING_MORE = R.layout.infinite_loading;
    private static final int NO_MORE = R.layout.infinite_no_more;
    private static final int MOVIE_DETAIL = R.layout.layout_movie_detail;
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
}
