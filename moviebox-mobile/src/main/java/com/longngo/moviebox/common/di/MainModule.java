package com.longngo.moviebox.common.di;

import android.content.Context;


import com.longngo.moviebox.data.backend.MovieBoxServiceApi;
import com.longngo.moviebox.data.backend.MovieBoxServiceFactory;
import com.longngo.moviebox.data.source.MoviesDataSource;
import com.longngo.moviebox.data.source.remote.MoviesRemoteDataSource;
import com.longngo.moviebox.FootballFanApplication;
import com.longngo.moviebox.common.schedulers.BaseSchedulerProvider;
import com.longngo.moviebox.common.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Long on 7/8/2016.
 */
@Module
public class MainModule {
    private Context context;
    private final FootballFanApplication application;

    public MainModule(FootballFanApplication application) {
        this.application = application;
    }

    @Provides @Singleton Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    MovieBoxServiceApi provideMovieBoxServiceApi() {
        return MovieBoxServiceFactory.makeService();
    }

    @Provides
    @Singleton
    BaseSchedulerProvider provideSchedulerProvider( SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }

    @Provides
    @Singleton
    MoviesDataSource provideCompetitionsDataSource(MoviesRemoteDataSource competitionsDataSource) {
        return competitionsDataSource;
    }





}

