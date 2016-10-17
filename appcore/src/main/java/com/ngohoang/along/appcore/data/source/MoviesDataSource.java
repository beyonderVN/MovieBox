package com.ngohoang.along.appcore.data.source;



import com.ngohoang.along.appcore.data.model.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by Long on 10/6/2016.
 */

public interface MoviesDataSource {


    Observable<List<Movie>> getMovieList(int page);

    void saveCompetition(Movie competition);
}
