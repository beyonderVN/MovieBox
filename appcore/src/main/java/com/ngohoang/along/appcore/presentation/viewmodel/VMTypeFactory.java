package com.ngohoang.along.appcore.presentation.viewmodel;


import android.view.View;

/**
 * Created by Long on 10/5/2016.
 */

public interface VMTypeFactory {
    int getType(MovieVM movieVM);


    int getType(LoadingMoreVM loadingMoreVM);

    int getType(NoMoreItemVM noMoreItemVM);

    int getType(MovieDetailVM movieDetailVM);

    int getType(TrailerMovieVM trailerMovieVM);


}
