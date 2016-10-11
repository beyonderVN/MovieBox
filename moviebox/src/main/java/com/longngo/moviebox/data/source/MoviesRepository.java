package com.longngo.moviebox.data.source;

/**
 * Created by Long on 10/6/2016.
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.longngo.moviebox.data.model.Movie;
import com.longngo.moviebox.data.source.local.MoviesLocalDataSource;
import com.longngo.moviebox.data.source.remote.MoviesRemoteDataSource;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Concrete implementation to load data from the data sources into a cache.
 * <p/>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
@Singleton
public class MoviesRepository implements MoviesDataSource {
    private static final String TAG = "MoviesRepository";

    @NonNull
    private final MoviesDataSource mCompetitionsRemoteDataSource;

    @NonNull
    private final MoviesDataSource mCompetitionsLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    Map<Integer, Movie> mCachedCompetitions;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    @Inject
    public MoviesRepository() {
        mCompetitionsRemoteDataSource = MoviesRemoteDataSource.getInstance();
        mCompetitionsLocalDataSource = MoviesLocalDataSource.getInstance();
    }





    @Override
    public Observable<List<Movie>> getMovieList(int page) {
        // Respond immediately with cache if available and not dirty
//        if (mCachedCompetitions != null && !mCacheIsDirty && mCachedCompetitions.values().size()!=0) {
//            return Observable.from(mCachedCompetitions.values()).toList();
//        } else if (mCachedCompetitions == null) {
//            mCachedCompetitions = new LinkedHashMap<>();
//        }

        Observable<List<Movie>> listObservable = getAndSaveRemoteCompetitions(page);
        return listObservable;
//        if (mCacheIsDirty) {
//            return listObservable;
//        } else {
//            // Query the local storage if available. If not, query the network.
//            Observable<List<Movie>> localTasks = getAndCacheLocalCompetitions();
//            return Observable.concat(localTasks, listObservable)
//                    .filter(new Func1<List<Movie>, Boolean>() {
//                        @Override
//                        public Boolean call(List<Movie> tasks) {
//                            return !tasks.isEmpty();
//                        }
//                    }).first();
//        }
    }

    @Override
    public void saveCompetition(Movie competition) {

    }

    private Observable<List<Movie>> getAndCacheLocalCompetitions(int page) {
            return mCompetitionsLocalDataSource.getMovieList(page)
                    .flatMap(new Func1<List<Movie>, Observable<List<Movie>>>() {
                        @Override
                        public Observable<List<Movie>> call(List<Movie> movieList) {
                            return Observable.from(movieList)
                                    .doOnNext(new Action1<Movie>() {
                                        @Override
                                        public void call(Movie movie) {
                                            mCachedCompetitions.put(movie.getId(), movie);
                                        }
                                    })
                                    .toList();
                        }
                    });
        }

        private Observable<List<Movie>> getAndSaveRemoteCompetitions(int page) {
            return mCompetitionsRemoteDataSource
                    .getMovieList(page)
                    .flatMap(new Func1<List<Movie>, Observable<List<Movie>>>() {
                        @Override
                        public Observable<List<Movie>> call(List<Movie> movieList) {
                            return Observable.from(movieList).doOnNext(new Action1<Movie>() {
                                @Override
                                public void call(Movie movie) {
                                    Log.d(TAG, "getAndSaveRemoteCompetitions: "+movie.toString());
//                                    mCompetitionsLocalDataSource.saveCompetition(competition);
//                                    mCachedCompetitions.put(movie.getId(), movie);
                                }
                            }).toList();
                        }
                    })
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.d(TAG, "call: "+throwable);
                        }
                    })
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            mCacheIsDirty = false;
                        }
                    });

        }

}
