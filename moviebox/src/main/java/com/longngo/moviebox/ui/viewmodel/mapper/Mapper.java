package com.longngo.moviebox.ui.viewmodel.mapper;

import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.ui.viewmodel.BaseVM;
import com.longngo.moviebox.ui.viewmodel.MovieVM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 10/5/2016.
 */

public class Mapper {
    public static BaseVM tranCompetition(Movie movie){
        return new MovieVM(movie);
    }

    public static List<BaseVM> tranCompetition(List<Movie> movieList){
        List<BaseVM> list = new ArrayList<>();
        for (Movie item :movieList) {
            list.add(tranCompetition(item));
        }
        return list;
    }




}
