package com.longngo.moviebox.data.source;

import com.longngo.moviebox.data.model.Movie;

import java.util.List;

import rx.Observable;

/**
 * Created by Long on 10/6/2016.
 */

public interface MoviesDataSource {

    Observable<List<Movie>> getMovieList();

    void saveCompetition(Movie competition);
}
