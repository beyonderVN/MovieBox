package com.longngo.moviebox.data.source.remote;

import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.data.source.MoviesDataSource;
import com.longngo.moviebox.FootballFanApplication;

import java.util.List;

import rx.Observable;

/**
 * Created by Long on 10/6/2016.
 */

public class MoviesRemoteDataSource implements MoviesDataSource {
    private static MoviesRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 1000;
    public static MoviesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MoviesRemoteDataSource() {}
    @Override
    public Observable<List<Movie>> getMovieList() {
        return FootballFanApplication.getMainComponent().dataManager().getCompetitions();
    }

    @Override
    public void saveCompetition(Movie competition) {

    }
}
