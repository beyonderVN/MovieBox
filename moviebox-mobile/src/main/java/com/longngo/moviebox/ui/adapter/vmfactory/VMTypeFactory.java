package com.longngo.moviebox.ui.adapter.vmfactory;

import android.view.View;

import com.longngo.moviebox.ui.adapter.viewholder.BaseViewHolder;
import com.longngo.moviebox.ui.viewmodel.LoadingMoreVM;
import com.longngo.moviebox.ui.viewmodel.MovieDetailVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;
import com.longngo.moviebox.ui.viewmodel.NoMoreItemVM;
import com.longngo.moviebox.ui.viewmodel.TrailerMovieVM;

/**
 * Created by Long on 10/5/2016.
 */

public interface VMTypeFactory {
    int getType(MovieVM movieVM);

    BaseViewHolder createHolder(int type, View view);

    int getType(LoadingMoreVM loadingMoreVM);

    int getType(NoMoreItemVM noMoreItemVM);

    int getType(MovieDetailVM movieDetailVM);

    int getType(TrailerMovieVM trailerMovieVM);
}
