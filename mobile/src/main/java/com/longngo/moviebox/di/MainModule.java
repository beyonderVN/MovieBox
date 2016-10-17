package com.longngo.moviebox.di;

import android.content.Context;

import com.longngo.moviebox.FootballFanApplication;
import com.ngohoang.along.appcore.common.schedulers.BaseSchedulerProvider;
import com.ngohoang.along.appcore.common.schedulers.SchedulerProvider;
import com.ngohoang.along.appcore.data.backend.MovieBoxServiceApi;
import com.ngohoang.along.appcore.data.backend.MovieBoxServiceFactory;

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
    BaseSchedulerProvider provideSchedulerProvider(SchedulerProvider schedulerProvider) {
        return schedulerProvider;
    }

//    @Provides
//    @Singleton
//    HolderFactory provideHolderFactory(HolderFactoryImpl holderFactory) {
//        return holderFactory;
//    }
//    @Provides
//    @Singleton
//    MoviesRemoteDataSource provideMoviesRemoteDataSource(MoviesRemoteDataSource competitionsDataSource) {
//        return competitionsDataSource;
//    }
////    @Provides
//    @Singleton
//    MoviesLocalDataSource provideMoviesLocalDataSource(MoviesLocalDataSource moviesLocalDataSource) {
//        return moviesLocalDataSource;
//    }
////    @Provides
////    @Singleton
////    MoviesRepository provideMoviesRepository(MoviesRepository moviesRepository) {
////        return moviesRepository;
////    }
//
//    @Provides
//    @Singleton
//    MovieBoxService provideMovieBoxService(MovieBoxService movieBoxService) {
//        return movieBoxService;
//    }





}

