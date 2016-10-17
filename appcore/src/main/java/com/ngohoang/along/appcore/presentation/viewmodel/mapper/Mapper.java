package com.ngohoang.along.appcore.presentation.viewmodel.mapper;


import com.ngohoang.along.appcore.data.model.Movie;
import com.ngohoang.along.appcore.presentation.viewmodel.BaseVM;
import com.ngohoang.along.appcore.presentation.viewmodel.MovieVM;
import com.ngohoang.along.appcore.presentation.viewmodel.TrailerMovieVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 10/5/2016.
 */

public class Mapper {
    public static BaseVM tranToMovieVM(Movie movie){
        return new MovieVM(movie);
    }
    public static BaseVM tranToMovieTrailerVM(Movie movie){
        TrailerMovieVM trailerMovieVM = new TrailerMovieVM(movie);
        trailerMovieVM.setFullSpan(true);
        return trailerMovieVM;
    }

    public static List<BaseVM> tranToVM(List<Movie> movieList){
        List<BaseVM> list = new ArrayList<>();

        for (Movie item :movieList) {
//            list.add(item.getVoteAverage()>5?tranToMovieTrailerVM(item):tranToMovieVM(item));

            if (item.getVoteAverage()>5){
                list.add(tranToMovieTrailerVM(item));

            }else {
                list.add(tranToMovieVM(item));

            }
        }
        return list;
    }




}
